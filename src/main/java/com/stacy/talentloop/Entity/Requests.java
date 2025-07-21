package com.stacy.talentloop.Entity;

import com.stacy.talentloop.DTO.RequestStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "requests")
public class Requests {

    @Id
    private String id;

    @DBRef
    private User sender;

    @DBRef
    private User receiver;

    private RequestStatus status = RequestStatus.PENDING;

    private String message;

    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime respondedAt;


}
