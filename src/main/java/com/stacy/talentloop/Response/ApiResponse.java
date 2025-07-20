package com.stacy.talentloop.Response;

public record ApiResponse(
        String message,
        Object data
) {
}
