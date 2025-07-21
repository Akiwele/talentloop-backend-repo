package com.stacy.talentloop.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "Please a Valid Credential")
        String identifier,

        @NotBlank(message = "User Password must be provided")
        String password
) {
}
