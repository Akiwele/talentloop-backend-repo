package com.stacy.talentloop.DTO;

import com.stacy.talentloop.Entity.Message;

import java.time.LocalDateTime;
import java.util.List;

public record NotificationDto(
        String message,
        LocalDateTime createdAt
) {
}
