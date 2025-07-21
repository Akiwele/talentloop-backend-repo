package com.stacy.talentloop.DTO;


import com.stacy.talentloop.Entity.User;

import java.time.LocalDateTime;

public record RequestDto(
        String id,
        User sender,
        User receiverId,
        String message,
        RequestStatus status,
        LocalDateTime sentAt,
        LocalDateTime respondAt
) {
}
