package com.stacy.talentloop.Controller;

import com.stacy.talentloop.DTO.RequestDto;
import com.stacy.talentloop.DTO.RequestStatus;
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
            @RequestParam("userId")String userId,
            @RequestParam("receiverId")String receiverId
    ){
        RequestDto response = requestService.sendRequest(userId, receiverId);
        return ResponseEntity
                .status(CREATED)
                .body(new ApiResponse("Request sent Successfully", response));
    }


    @PatchMapping("/update")
    public ResponseEntity<ApiResponse> approveRequest(
            @RequestParam("requestId")String requestId,
            @RequestParam("userId")String userId,
            @RequestParam("status")RequestStatus status
            ){
        requestService.updateRequestStatus(requestId, userId,status );
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse("Request updated Successfully", null));
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
