package com.github.immotarity.domain.user.domain.controller;

import com.github.immotarity.domain.user.domain.controller.dto.SignupRequest;
import com.github.immotarity.domain.user.domain.controller.dto.SignupResponse;
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

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest signupRequest) {

    }

}
