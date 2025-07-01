package com.Bertazz1.demo_park_api.repository;

import com.Bertazz1.demo_park_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u.role FROM User u WHERE u.username LIKE :username")
    User.Role findRoleByUsername(String username);
}