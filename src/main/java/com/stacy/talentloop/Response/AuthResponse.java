package com.stacy.talentloop.Response;

public record AuthResponse(
        String userId,
        String username,
        String fullName,
        String email,
        String profileUrl,
        String token
) {
}
