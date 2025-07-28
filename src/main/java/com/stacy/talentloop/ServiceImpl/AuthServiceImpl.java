package com.stacy.talentloop.ServiceImpl;

import com.stacy.talentloop.Entity.User;
import com.stacy.talentloop.Exceptions.EntityAlreadyExists;
import com.stacy.talentloop.Exceptions.EntityNotFoundException;
import com.stacy.talentloop.Repository.UserRepository;
import com.stacy.talentloop.Requests.AuthRequest;
import com.stacy.talentloop.Requests.CreateProfileRequest;
import com.stacy.talentloop.Requests.RegisterRequest;
import com.stacy.talentloop.Response.AuthResponse;
import com.stacy.talentloop.Security.JwtService;
import com.stacy.talentloop.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public AuthResponse authenticate(AuthRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.identifier());
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByUsername(request.identifier());
        }

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            request.password()
                    )
            );

            var jwtToken = jwtService.generateJwtToken(user);
            return new AuthResponse(
                    user.getId(),
                    user.getRealUsername(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getProfileImageUrl(),
                    jwtToken
            );
        }

        throw new EntityNotFoundException("User not found with provided email or username.");
    }


    @Override
    public AuthResponse register(RegisterRequest request) {
        Optional<User> emailExists = userRepository.findByEmail(request.email());
        Optional<User> userNameExists = userRepository.findByUsername(request.username());
        if (emailExists.isPresent()){
            throw new EntityAlreadyExists("A User Already exits with this email");
        } else if (userNameExists.isPresent()) {
            throw new EntityAlreadyExists("A User Already exists with this username");
        }

        var user = User.builder()
                .fullName(request.fullName())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .skills(request.skills())
                .profileImageUrl(request.profileUrl())
                .bio(request.bio())
                .role(request.role())
                .availability(request.availability())
                .build();

        User createdUser = userRepository.save(user);
        var jwtToken = jwtService.generateJwtToken(user);
        return new AuthResponse(
                createdUser.getId(),
                createdUser.getRealUsername(),
                createdUser.getFullName(),
                createdUser.getEmail(),
                createdUser.getProfileImageUrl(),
                jwtToken
        );
    }


    @Override
    public AuthResponse createUserProfile(CreateProfileRequest request, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setAvailability(request.availability());
        user.setBio(request.bio());
        user.setProfileImageUrl(request.profileUrl());
        user.setSkills(request.skills());

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateJwtToken(savedUser);

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getRealUsername(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getProfileImageUrl(),
                jwtToken
        );
    }
}
