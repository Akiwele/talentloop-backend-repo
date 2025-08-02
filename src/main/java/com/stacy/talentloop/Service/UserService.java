package com.stacy.talentloop.Service;

import com.stacy.talentloop.DTO.ConnectDto;
import com.stacy.talentloop.DTO.UserDto;
import com.stacy.talentloop.Requests.UpdateUserRequest;
import com.stacy.talentloop.Response.AuthResponse;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(String userId);
    UserDto getUser(String userId);
    AuthResponse updateUser(UpdateUserRequest request, String userId);
    void likeUser(String userId, String instructorId);
    void disLike(String userId, String instructorId);
    List<ConnectDto> connections(String userId);
}
