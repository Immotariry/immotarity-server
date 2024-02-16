package com.github.immotarity.domain.user.domain.service;

import com.github.immotarity.domain.user.domain.User;
import com.github.immotarity.domain.user.domain.controller.dto.LoginRequest;
import com.github.immotarity.domain.user.domain.controller.dto.SignupRequest;
import com.github.immotarity.domain.user.domain.repository.UserRepository;
import com.github.immotarity.global.manager.CookieManager;
import com.github.immotarity.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.immotarity.domain.user.domain.Role.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final CookieManager cookieManager;

    @Transactional
    public void join(SignupRequest request) {

        String email = request.getEmail();
        String username = request.getUsername();
        String password = passwordEncoder.encode(request.getPassword());

        if (userRepository.existsByEmail(email)) throw new RuntimeException();

        User user = User.builder()
                .email(email)
                .username(username)
                .password(password)
                .role(STAFF)
                .build();

        userRepository.save(user);
    }

    @Transactional
    public void login(HttpServletResponse httpServletResponse, LoginRequest request) {

        String email = request.getEmail();
        String password = request.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(RuntimeException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }

        cookieManager.addTokenCookie(httpServletResponse, JwtTokenProvider.ACCESS_KEY, tokenProvider.getToken(user.getEmail()).getAccessToken(), tokenProvider.accessExp, true);
    }

}
