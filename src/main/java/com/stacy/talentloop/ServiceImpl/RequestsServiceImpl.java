package com.stacy.talentloop.ServiceImpl;

import com.stacy.talentloop.DTO.RequestDto;
import com.stacy.talentloop.DTO.RequestStatus;
import com.stacy.talentloop.Entity.Requests;
import com.stacy.talentloop.Entity.User;
import com.stacy.talentloop.Exceptions.EntityNotFoundException;
import com.stacy.talentloop.Mappers.RequestMapper;
import com.stacy.talentloop.Repository.RequestsRepository;
import com.stacy.talentloop.Repository.UserRepository;
import com.stacy.talentloop.Service.RequestService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestsServiceImpl implements RequestService {

    private final RequestsRepository repository;
    private final UserRepository userRepository;
    private final RequestMapper mapper;


    @Override
    public RequestDto sendRequest(String senderId, String receiverId) {
        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("Cannot send request to yourself");
        }

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        repository.findBySender_IdAndReceiver_Id(senderId, receiverId)
                .filter(r -> r.getStatus() == RequestStatus.PENDING)
                .ifPresent(r -> {
                    throw new IllegalStateException("Pending request already exists");
                });

        Requests request = Requests.builder()
                .sender(sender)
                .receiver(receiver)
                .message("Wants to connect.")
                .status(RequestStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
            var savedRequest = repository.save(request);
        return mapper.apply(savedRequest);
    }


    @Override
    public RequestDto approveRequest(String requestId, String receiverId) {
        var request = repository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));

        if (!request.getReceiver().getId().equals(receiverId)) {
            throw new IllegalArgumentException("Not authorized");
        }

        request.setStatus(RequestStatus.APPROVED);
        request.setMessage("Approved your request.");
        request.setRespondedAt(LocalDateTime.now());
        var approvedRequest = repository.save(request);
        return mapper.apply(approvedRequest);
    }


    @Override
    public RequestDto declineRequest(String requestId, String receiverId) {
        var request = repository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));

        if (!request.getReceiver().getId().equals(receiverId)) {
            throw new IllegalArgumentException("Not authorized");
        }

        request.setStatus(RequestStatus.DECLINED);
        request.setMessage(String.format("%s declined your request", request.getReceiver().getRealUsername()));
        request.setRespondedAt(LocalDateTime.now());
        var declinedRequest = repository.save(request);
        return mapper.apply(declinedRequest);
    }


    @Override
    public List<RequestDto> getAllRequestsForUser(String receiverId) {
        return repository.findByReceiver_Id(receiverId)
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }


    @Override
    public List<RequestDto> getPendingRequestsForUser(String receiverId) {
        return repository.findByReceiver_IdAndStatus(receiverId, RequestStatus.PENDING)
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }


    @Override
    public List<RequestDto> getApprovedRequestsForUser(String receiverId) {
        return repository.findByReceiver_IdAndStatus(receiverId, RequestStatus.APPROVED)
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }


    @Override
    public List<RequestDto> getRequestsSentByUser(String senderId) {
        return repository.findBySender_Id(senderId)
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}
