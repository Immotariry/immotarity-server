package com.github.immotarity.domain.user.domain.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignupRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
