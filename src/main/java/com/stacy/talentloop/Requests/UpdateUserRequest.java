package com.stacy.talentloop.Requests;

import com.stacy.talentloop.DTO.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateUserRequest(
        @NotBlank(message = "Username must be provided")
        String username,

        @NotBlank(message = "User full name must be provided")
        String fullName,

        @Email(message = "Please provide a valid email")
        String email,
        String bio,
        UserRole role,
        String availability,
        List<String> skills,
        String profileImageUrl
) {
}
