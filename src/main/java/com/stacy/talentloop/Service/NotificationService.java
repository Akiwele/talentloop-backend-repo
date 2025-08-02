package com.stacy.talentloop.Service;

import com.stacy.talentloop.DTO.NotificationDto;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> getNotifications(String userId);
}
