package com.stacy.talentloop.Mappers;

import com.stacy.talentloop.DTO.RequestDto;
import com.stacy.talentloop.Entity.Requests;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class RequestMapper implements Function<Requests, RequestDto> {
    private final UserMapper userMapper;
    @Override
    public RequestDto apply(Requests requests) {
        return new RequestDto(
                requests.getId(),
                userMapper.apply(requests.getSender()),
                userMapper.apply(requests.getReceiver()),
                requests.getMessage(),
                requests.getStatus(),
                requests.getCreatedAt()
        );
    }
}
