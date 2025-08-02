package com.stacy.talentloop.ServiceImpl;

import com.stacy.talentloop.DTO.RequestDto;
import com.stacy.talentloop.DTO.RequestStatus;
import com.stacy.talentloop.Entity.Message;
import com.stacy.talentloop.Entity.Notification;
import com.stacy.talentloop.Entity.Requests;
import com.stacy.talentloop.Entity.User;
import com.stacy.talentloop.Exceptions.EntityAlreadyExists;
import com.stacy.talentloop.Exceptions.EntityNotFoundException;
import com.stacy.talentloop.Mappers.RequestMapper;
import com.stacy.talentloop.Repository.NotificationRepository;
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
    private final NotificationRepository notificationRepository;
    private final RequestMapper mapper;


    @Override
    public RequestDto sendRequest(String userId, String receiverId) {
        if (userId.equals(receiverId)) {
            throw new IllegalArgumentException("Cannot send request to self");
        }


        boolean exists = repository.findBySenderIdAndReceiverIdAndStatus(
                userId, receiverId, RequestStatus.PENDING
        ).isPresent();

        if (exists) {
            throw new EntityAlreadyExists("A pending request already exist.");
        }

        User sender = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        String message = String.format(
                "%s wants to connect",
                sender.getRealUsername()
        );

        Requests request = Requests.builder()
                .sender(sender)
                .receiver(receiver)
                .message(message)
                .status(RequestStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        var newRequest = repository.save(request);
        return mapper.apply(newRequest);
    }


    @Override
    public void updateRequestStatus(String requestId, String userId, RequestStatus status) {
        Requests request = repository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));

        if (!userId.equals(request.getReceiver().getId())) {
            throw new IllegalArgumentException("You are not permitted to change request status");
        }

        if (status != RequestStatus.APPROVED && status != RequestStatus.DECLINED) {
            throw new IllegalArgumentException("Invalid request status update");
        }

        User receiver = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        request.setStatus(status);
        repository.save(request);

        if (status == RequestStatus.APPROVED) {

            User sender = userRepository.findById(request.getSender().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Sender not found"));

            if (!sender.getConnections().contains(receiver)) {
                sender.getConnections().add(receiver);
                userRepository.save(sender);
            }
        }

        String message = status == RequestStatus.APPROVED
                ? String.format("%s accepted your request. This is their email: %s",
                receiver.getRealUsername(),
                receiver.getEmail())
                : String.format("%s declined your request",
                receiver.getRealUsername());

        Notification notification = Notification.builder()
                .participants(List.of(request.getSender(), receiver))
                .messages(List.of(
                        new Message(
                                receiver,
                                request.getSender(),
                                message,
                                LocalDateTime.now()
                        )
                ))
                .build();

        notificationRepository.save(notification);
    }


    @Override
    public List<RequestDto> getAllRequestsForUser(String userId) {
        return repository.findByReceiverIdAndStatus(userId, RequestStatus.PENDING)
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }


    @Override
    public List<RequestDto> getRequestsSentByUser(String userId) {
        return repository.findBySenderIdAndStatus(userId, RequestStatus.PENDING)
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }


}
