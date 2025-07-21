package com.stacy.talentloop.Controller;

import com.stacy.talentloop.DTO.UserDto;
import com.stacy.talentloop.Requests.UpdateUserRequest;
import com.stacy.talentloop.Response.ApiResponse;
import com.stacy.talentloop.Service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getUsers(){
        List<UserDto> response = userService.getUsers();
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
        UserDto response = userService.updateUser(request, userId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse("User Profile Updated successfully", response));
    }
}
