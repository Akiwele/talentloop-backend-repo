package com.stacy.talentloop.DTO;



import java.time.LocalDateTime;

public record RequestDto(
        String id,
        UserDto sender,
        UserDto receiver,
        String message,
        RequestStatus status,
        LocalDateTime sentAt

) {
}
