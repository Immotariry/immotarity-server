package com.github.immotarity.global.security.jwt;

import com.github.immotarity.global.manager.CookieManager;
import com.github.immotarity.global.security.jwt.dto.TokenResponse;
import com.github.immotarity.global.security.principle.AuthDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${spring.jwt.secretKey}")
    private String secretKey;
    @Value("${spring.jwt.accessExp}")
    public Long accessExp;

    private final AuthDetailsService authDetailsService;
    private final CookieManager cookieManager;
    public static final String ACCESS_KEY = "access_token";

    public TokenResponse getToken(String email) {
        String accessToken = generateToken(email, accessExp, ACCESS_KEY);

        return new TokenResponse(accessToken);
    }

    private String generateToken(String email, long expiration, String type) {
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, secretKey)
                .setSubject(email)
                .setHeaderParam("typ", type)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = cookieManager.getCookieValue(request, ACCESS_KEY);
        return parseToken(bearer);
    }

    public String parseToken(String bearerToken) {
        return bearerToken;
    }

    public UsernamePasswordAuthenticationToken authorization(String token) {
        UserDetails userDetails = authDetailsService.loadUserByUsername(getTokenSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getTokenSubject(String subject) {
        return getTokenBody(subject).getSubject();
    }

    private Claims getTokenBody(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
