package com.github.immotarity.global.security.principle;

import com.github.immotarity.domain.user.domain.User;
import com.github.immotarity.domain.user.domain.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

    private final UserJpaRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(RuntimeException::new);
        return new AuthDetails(user);
    }
}