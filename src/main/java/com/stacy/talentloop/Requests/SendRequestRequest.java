package com.stacy.talentloop.Requests;

public record SendRequestRequest(
        String senderName,
        String receiverId,
        String message
) {
}
