package com.stacy.talentloop.Requests;

import java.util.List;

public record UpdateUserRequest(
        String username,
        String fullName,
        String email,
        String bio,
        String availability,
        List<String> skills,
        String profileImageUrl
) {
}
