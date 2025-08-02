package com.stacy.talentloop.ServiceImpl;

import com.stacy.talentloop.DTO.ConnectDto;
import com.stacy.talentloop.DTO.UserDto;
import com.stacy.talentloop.DTO.UserRole;
import com.stacy.talentloop.Entity.User;
import com.stacy.talentloop.Exceptions.EntityNotFoundException;
import com.stacy.talentloop.Mappers.ConnectionMapper;
import com.stacy.talentloop.Mappers.UserMapper;
import com.stacy.talentloop.Repository.UserRepository;
import com.stacy.talentloop.Requests.UpdateUserRequest;
import com.stacy.talentloop.Response.AuthResponse;
import com.stacy.talentloop.Security.JwtService;
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
    private final ConnectionMapper connectionMapper;
    private final JwtService jwtService;

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
    public AuthResponse updateUser(UpdateUserRequest request, String userId) {
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
        var token = jwtService.generateJwtToken(user);
        return new AuthResponse(
                user.getId(),
                user.getRealUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getProfileImageUrl(),
                token
        );
    }

    @Override
    public void likeUser(String userId, String instructorId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        if (!instructor.getLikedBy().contains(userId)) {
            instructor.getLikedBy().add(userId);
            instructor.getDisLikedBy().remove(userId);
            userRepository.save(instructor);
        }
    }


    @Override
    public void disLike(String userId, String instructorId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        if (!instructor.getDisLikedBy().contains(userId)) {
            instructor.getDisLikedBy().add(userId);
            instructor.getLikedBy().remove(userId);
            userRepository.save(instructor);

        }
    }


    @Override
    public List<ConnectDto> connections(String userId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return user.getConnections()
                .stream()
                .map(connectionMapper)
                .collect(Collectors.toList());
    }

    private <T> boolean updateIfNotNullAndChanged(T newValue, T currentValue, Consumer<T> updater) {
        if (newValue != null && !newValue.equals(currentValue)) {
            updater.accept(newValue);
            return true;
        }
        return false;
    }
}
