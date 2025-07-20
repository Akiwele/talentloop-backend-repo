package com.stacy.talentloop.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @Email(message = "Please a Valid Email")
        String email,

        @NotBlank(message = "User Password must be provided")
        String password
) {
}
