package com.stacy.talentloop.DTO;

import java.util.List;

public record UserDto(
        String id,
        String username,
        String fullName,
        String email,
        String bio,
        String availability,
        List<String> skills,
        String profileImageUrl
) {
}
