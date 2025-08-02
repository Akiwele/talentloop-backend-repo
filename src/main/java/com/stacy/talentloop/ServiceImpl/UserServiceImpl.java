package com.stacy.talentloop.ServiceImpl;

import com.stacy.talentloop.DTO.UserDto;
import com.stacy.talentloop.DTO.UserRole;
import com.stacy.talentloop.Entity.User;
import com.stacy.talentloop.Exceptions.EntityNotFoundException;
import com.stacy.talentloop.Mappers.UserMapper;
import com.stacy.talentloop.Repository.UserRepository;
import com.stacy.talentloop.Requests.UpdateUserRequest;
import com.stacy.talentloop.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getUsers(String userId) {
        return userRepository.findByRoleAndIdNot(UserRole.LEARNANDTEACH, userId)
                .stream()
                .map(userMapper)
                .collect(Collectors.toList());
    }


    @Override
    public UserDto getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return userMapper.apply(user);
    }


    @Override
    public UserDto updateUser(UpdateUserRequest request, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        boolean updated = false;
        updated |= updateIfNotNullAndChanged(request.username(), user.getUsername(), user::setUsername);
        updated |= updateIfNotNullAndChanged(request.fullName(), user.getFullName(), user::setFullName);
        updated |= updateIfNotNullAndChanged(request.email(), user.getEmail(), user::setEmail);
        updated |= updateIfNotNullAndChanged(request.bio(), user.getBio(), user::setBio);
        updated |= updateIfNotNullAndChanged(request.availability(), user.getAvailability(), user::setAvailability);
        updated |= updateIfNotNullAndChanged(request.skills(), user.getSkills(), user::setSkills);
        updated |= updateIfNotNullAndChanged(request.profileImageUrl(), user.getProfileImageUrl(), user::setProfileImageUrl);
        updated |= updateIfNotNullAndChanged(request.role(), user.getRole(), user::setRole);

        if (updated) {
            user = userRepository.save(user);
        }

        return userMapper.apply(user);
    }


    private <T> boolean updateIfNotNullAndChanged(T newValue, T currentValue, Consumer<T> updater) {
        if (newValue != null && !newValue.equals(currentValue)) {
            updater.accept(newValue);
            return true;
        }
        return false;
    }
}
