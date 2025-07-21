package com.stacy.talentloop.Service;



import com.stacy.talentloop.DTO.RequestDto;
import org.apache.coyote.Request;

import java.util.List;

public interface RequestService {
    RequestDto sendRequest(String senderId, String receiverId);
    RequestDto approveRequest(String requestId, String receiverId);
    RequestDto declineRequest(String requestId, String receiverId);
    List<RequestDto> getAllRequestsForUser(String receiverId);
    List<RequestDto> getPendingRequestsForUser(String receiverId);
    List<RequestDto> getApprovedRequestsForUser(String receiverId);
    List<RequestDto> getRequestsSentByUser(String senderId);
}
