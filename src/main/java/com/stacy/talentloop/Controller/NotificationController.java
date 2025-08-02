package com.stacy.talentloop.Controller;


import com.stacy.talentloop.DTO.NotificationDto;
import com.stacy.talentloop.Response.ApiResponse;
import com.stacy.talentloop.Service.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {
    private final NotificationService service;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getNotifications(
            @RequestParam("userId") String userId
    ){
        List<NotificationDto> response = service.getNotifications(userId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse("Success", response));
    }
}
