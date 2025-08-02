package com.stacy.talentloop.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @DBRef
    private User sender;
    @DBRef
    private User receiver;
    private String message;

    @CreatedDate
    private LocalDateTime createdAt;
}
