package com.example.User.Repository;

import com.example.User.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUserId(Long userId);
    Optional<Users> findByLogin(String login);
}
