package com.stacy.talentloop.Repository;

import com.stacy.talentloop.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String username);
    Optional<User> findByUsername(String username);
}
