package com.stacy.talentloop.Controller;

import com.stacy.talentloop.DTO.RequestDto;
import com.stacy.talentloop.Response.ApiResponse;
import com.stacy.talentloop.Service.RequestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
@SecurityRequirement(name = "bearerAuth")
public class RequestsController {

    private final RequestService requestService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse> sendRequest(
            @RequestParam("senderId")String senderId,
            @RequestParam("receiverId")String receiverId
    ){
        RequestDto response = requestService.sendRequest(senderId, receiverId);
        return ResponseEntity
                .status(CREATED)
                .body(new ApiResponse("Request sent Successfully", response));
    }


    @PatchMapping("/approve")
    public ResponseEntity<ApiResponse> approveRequest(
            @RequestParam("requestId")String requestId,
            @RequestParam("receiverId")String receiverId
    ){
        RequestDto response = requestService.approveRequest(requestId, receiverId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse("Request approved Successfully", response));
    }


    @PatchMapping("/decline")
    public ResponseEntity<ApiResponse> declineRequest(
            @RequestParam("senderId")String senderId,
            @RequestParam("receiverId")String receiverId
    ){
        RequestDto response = requestService.declineRequest(senderId, receiverId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse("Request declined Successfully", response));
    }


    @GetMapping("/")
    public ResponseEntity<ApiResponse> getRequests(
            @RequestParam("userId")String userId
    ){
        List<RequestDto> response = requestService.getAllRequestsForUser(userId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse("Success", response));
    }


    @GetMapping("/pending")
    public ResponseEntity<ApiResponse> getPendingRequests(
            @RequestParam("userId")String userId
    ){
        List<RequestDto> response = requestService.getPendingRequestsForUser(userId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse("Success", response));
    }


    @GetMapping("/approved")
    public ResponseEntity<ApiResponse> getApprovedRequests(
            @RequestParam("userId")String userId
    ){
        List<RequestDto> response = requestService.getApprovedRequestsForUser(userId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse("Success", response));
    }


    @GetMapping("/sent")
    public ResponseEntity<ApiResponse> getSentRequests(
            @RequestParam("userId")String userId
    ){
        List<RequestDto> response = requestService.getRequestsSentByUser(userId);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse("Success", response));
    }


}
