package com.github.immotarity.domain.user.domain.service;

import com.github.immotarity.domain.user.domain.Role;
import com.github.immotarity.domain.user.domain.User;
import com.github.immotarity.domain.user.domain.controller.dto.SignupRequest;
import com.github.immotarity.domain.user.domain.repository.UserRepository;
import com.github.immotarity.global.security.jwt.JwtTokenProvider;
import com.github.immotarity.global.security.jwt.dto.TokenResponse;
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

    @Transactional
    public TokenResponse join(SignupRequest request) {

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

        User savedUser = userRepository.save(user);

        return tokenProvider.getToken(savedUser.getEmail());
    }

}
