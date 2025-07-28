package com.stacy.talentloop.Requests;

import com.stacy.talentloop.DTO.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record RegisterRequest(
        @NotBlank(message = "User FullName must be provided")
        String fullName,
        String username,

        @Email(message = "Please a valid Email")
        String email,
        @NotBlank(message = "User Password must be provided")
        String password,

        UserRole role,
        String bio,
        String profileUrl,
        List<String> skills

) {
}
