package com.stacy.talentloop.Requests;

import java.util.List;

public record CreateProfileRequest(
        String availability,
        String bio,
        String profileUrl,
        List<String> skills
) {
}
