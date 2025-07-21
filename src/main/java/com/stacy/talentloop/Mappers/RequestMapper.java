package com.stacy.talentloop.Mappers;

import com.stacy.talentloop.DTO.RequestDto;
import com.stacy.talentloop.Entity.Requests;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RequestMapper implements Function<Requests, RequestDto> {
    @Override
    public RequestDto apply(Requests requests) {
        return new RequestDto(
                requests.getId(),
                requests.getSender(),
                requests.getReceiver(),
                requests.getMessage(),
                requests.getStatus(),
                requests.getCreatedAt(),
                requests.getRespondedAt()
        );
    }
}
