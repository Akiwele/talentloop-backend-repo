package com.stacy.talentloop.Controller;

import com.stacy.talentloop.Requests.AuthRequest;
import com.stacy.talentloop.Requests.CreateProfileRequest;
import com.stacy.talentloop.Requests.RegisterRequest;
import com.stacy.talentloop.Response.ApiResponse;
import com.stacy.talentloop.Response.AuthResponse;
import com.stacy.talentloop.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticateUser(
            @Validated
            @RequestBody AuthRequest request
            ){
        AuthResponse authResponse = authService.authenticate(request);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse("User login Successful", authResponse));
    }


    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(
            @Validated
            @RequestBody RegisterRequest request
    ){
        AuthResponse authResponse = authService.register(request);
        return ResponseEntity
                .status(CREATED)
                .body(new ApiResponse("User registered successfully", authResponse));
    }


    @PatchMapping("/create-profile")
    public ResponseEntity<ApiResponse> createUserProfile(
            @RequestBody CreateProfileRequest request,
            @RequestParam("userId") String userId
            ){
        AuthResponse response = authService.createUserProfile(request, userId);
        return  ResponseEntity
                .status(CREATED)
                .body(new ApiResponse("User Profile Created Successfully", response));
    }
}
