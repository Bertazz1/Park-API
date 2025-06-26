package com.Bertazz1.demo_park_api.service;
import java.util.List;


import com.Bertazz1.demo_park_api.exception.UsernameUniqueException;
import com.Bertazz1.demo_park_api.repository.UserRepository;
import com.Bertazz1.demo_park_api.entity.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new UsernameUniqueException(String.format("Username '%s' is already in use", user.getUsername()));
        }

    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
            new RuntimeException("User not found with id: " + id));
    }

    @Transactional
    public User updatePassword(Long id, String oldPassword, String newPassword, String confirmNewPassword) {
        if (!newPassword.equals(confirmNewPassword)) {
            throw new RuntimeException("The new password is different from the password confirmation");
        }

        User user = findById(id);
        if (!user.getPassword().equals(oldPassword)){
            throw new RuntimeException("Your password does not match");

        }
        user.setPassword(newPassword);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
