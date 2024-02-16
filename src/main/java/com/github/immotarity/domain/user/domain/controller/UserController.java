package com.github.immotarity.domain.user.domain.controller;

import com.github.immotarity.domain.user.domain.controller.dto.SignupRequest;
import com.github.immotarity.domain.user.domain.controller.dto.SignupResponse;
import com.github.immotarity.domain.user.domain.service.UserService;
import com.github.immotarity.global.security.jwt.dto.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/immotarity/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest signupRequest) {
        TokenResponse tokenResponse = userService.join(signupRequest);
        return ResponseEntity.ok(new SignupResponse(tokenResponse.getAccessToken()));
    }

}
