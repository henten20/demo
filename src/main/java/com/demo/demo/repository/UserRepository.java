package com.demo.demo.repository;

import java.util.Optional;
import java.util.UUID;

import com.demo.demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Henry Ton
 * @since 0.0.1
 */
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
