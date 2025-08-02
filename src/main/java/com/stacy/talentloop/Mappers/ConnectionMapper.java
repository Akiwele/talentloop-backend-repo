package com.stacy.talentloop.Mappers;

import com.stacy.talentloop.DTO.ConnectDto;
import com.stacy.talentloop.Entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ConnectionMapper implements Function<User, ConnectDto> {
    @Override
    public ConnectDto apply(User user) {
        return new ConnectDto(
                user.getId(),
                user.getRealUsername(),
                user.getEmail(),
                user.getProfileImageUrl()
        );
    }
}
