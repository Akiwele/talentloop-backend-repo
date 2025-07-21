package com.stacy.talentloop.Service;

import com.stacy.talentloop.DTO.UserDto;
import com.stacy.talentloop.Requests.UpdateUserRequest;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    UserDto getUser(String userId);
    UserDto updateUser(UpdateUserRequest request, String userId);
}
