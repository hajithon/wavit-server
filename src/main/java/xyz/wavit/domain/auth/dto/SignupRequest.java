package xyz.wavit.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
        @NotBlank String name, @NotBlank String nickname, @NotBlank String username, @NotBlank String password) {}
