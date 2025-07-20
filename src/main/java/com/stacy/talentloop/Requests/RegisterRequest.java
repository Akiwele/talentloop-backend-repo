package com.stacy.talentloop.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "User FullName must be provided")
        String fullName,
        String username,

        @Email(message = "Please a valid Email")
        String email,

        @NotBlank(message = "User Password must be provided")
        String password
) {
}
