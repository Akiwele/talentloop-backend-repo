package com.stacy.talentloop.Mappers;

import com.stacy.talentloop.DTO.UserDto;
import com.stacy.talentloop.Entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserMapper implements Function<User, UserDto> {

    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getId(),
                user.getRealUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getBio(),
                user.getRole(),
                user.getAvailability(),
                user.getSkills(),
                user.getProfileImageUrl(),
                user.getLikedBy(),
                user.getDisLikedBy(),
                user.getConnections()
        );
    }
}
