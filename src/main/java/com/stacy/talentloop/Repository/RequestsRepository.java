package com.stacy.talentloop.Repository;

import com.stacy.talentloop.DTO.RequestStatus;
import com.stacy.talentloop.Entity.Requests;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestsRepository extends MongoRepository<Requests, String> {
    Optional<Requests> findBySender_IdAndReceiver_Id(String senderId, String receiverId);
    List<Requests> findByReceiver_Id(String receiverId);
    List<Requests> findByReceiver_IdAndStatus(String receiverId, RequestStatus requestStatus);
    List<Requests> findBySender_Id(String senderId);
}
