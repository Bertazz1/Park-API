package com.Bertazz1.demo_park_api.service;


import com.Bertazz1.demo_park_api.repository.UserRepository;
import com.Bertazz1.demo_park_api.entity.User;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
            new RuntimeException("User not found with id: " + id));
    }

    @Transactional
    public User updatePassword(Long id, String newPassword) {
        User user = findById(id);
        user.setPassword(newPassword);
        return user;
    }
}
