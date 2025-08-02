package com.stacy.talentloop.Mappers;

import com.stacy.talentloop.DTO.NotificationDto;
import com.stacy.talentloop.Entity.Message;
import com.stacy.talentloop.Entity.Notification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class NotificationMapper implements Function<Message, NotificationDto> {
    @Override
    public NotificationDto apply(Message message) {
        return new NotificationDto(
                message.getMessage(),
                message.getCreatedAt()
        );
    }
}
