package com.stacy.talentloop.Service;



import com.stacy.talentloop.DTO.RequestDto;
import com.stacy.talentloop.DTO.RequestStatus;

import java.util.List;

public interface RequestService {
    RequestDto sendRequest(String senderId, String receiverId);
    void updateRequestStatus(String requestId, String receiverId, RequestStatus status);
    List<RequestDto> getAllRequestsForUser(String receiverId);
    List<RequestDto> getRequestsSentByUser(String senderId);
}
