package com.stacy.talentloop.ServiceImpl;

import com.stacy.talentloop.DTO.NotificationDto;
import com.stacy.talentloop.Entity.Notification;
import com.stacy.talentloop.Mappers.NotificationMapper;
import com.stacy.talentloop.Repository.NotificationRepository;
import com.stacy.talentloop.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper mapper;

    @Override
    public List<NotificationDto> getNotifications(String userId) {
        List<Notification> notifications = notificationRepository.findByParticipants_Id(userId);

        return notifications.stream()
                .flatMap(notification -> notification.getMessages().stream())
                .filter(message -> userId.equals(message.getReceiver().getId()))
                .map(mapper)
                .collect(Collectors.toList());
    }
}
