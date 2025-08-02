package com.stacy.talentloop.Repository;

import com.stacy.talentloop.Entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByParticipants_Id(String userId);
}
