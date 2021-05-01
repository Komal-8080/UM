package com.usermgmt.user.repository;

import com.usermgmt.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(UUID id);

    Optional<User> findByUserName(String userName);

    Optional<User> findByEmailId(String emailId);
}
