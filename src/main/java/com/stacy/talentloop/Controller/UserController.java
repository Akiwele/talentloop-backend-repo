package com.stacy.talentloop.Controller;

import com.stacy.talentloop.DTO.UserDto;
import com.stacy.talentloop.Entity.User;
import com.stacy.talentloop.Repository.UserRepository;
import com.stacy.talentloop.Requests.UpdateUserRequest;
import com.stacy.talentloop.Response.ApiResponse;
import com.stacy.talentloop.Response.AuthResponse;
import com.stacy.talentloop.Service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getUsers(
            @RequestParam("userId") String userId
    ){
        List<UserDto> response = userService.getUsers(userId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse("Success", response));
    }


    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getUser(@RequestParam("userId") String userId){
        UserDto response = userService.getUser(userId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse("Success", response));
    }


    @PutMapping("/update-profile")
    public ResponseEntity<ApiResponse> updateUser(
            @RequestParam("userId") String userId,
            @RequestBody UpdateUserRequest request
            ){
        AuthResponse response = userService.updateUser(request, userId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse("User Profile Updated successfully", response));
    }


    @PatchMapping("/like")
    public ResponseEntity<ApiResponse> likeUser(
            @RequestParam("userId") String userId,
            @RequestParam("instructorId") String instructorId
    ) {
        userService.likeUser(userId, instructorId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse("Liked successfully",null));
    }


    @PatchMapping("/unlike")
    public ResponseEntity<ApiResponse> unLikeUser(
            @RequestParam("userId") String userId,
            @RequestParam("instructorId") String instructorId
    ) {
        userService.disLike(userId, instructorId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse("Unliked successfully",null));
    }
}
