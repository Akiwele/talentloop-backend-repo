package com.stacy.talentloop.Repository;

import com.stacy.talentloop.DTO.RequestStatus;
import com.stacy.talentloop.Entity.Requests;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface RequestsRepository extends MongoRepository<Requests, String> {
    List<Requests> findByReceiverIdAndStatus(String userId, RequestStatus requestStatus);
    List<Requests> findBySenderIdAndStatus(String userId, RequestStatus requestStatus);
    Optional<Requests> findBySenderIdAndReceiverIdAndStatus(String senderId, String receiverId, RequestStatus status);
}
