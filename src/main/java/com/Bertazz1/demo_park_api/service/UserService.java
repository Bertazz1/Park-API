package com.Bertazz1.demo_park_api.service;
import java.util.List;


import com.Bertazz1.demo_park_api.exception.EntityNotFoundException;
import com.Bertazz1.demo_park_api.exception.PasswordInvalidException;
import com.Bertazz1.demo_park_api.exception.UsernameUniqueException;
import com.Bertazz1.demo_park_api.repository.UserRepository;
import com.Bertazz1.demo_park_api.entity.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new UsernameUniqueException(String.format("Username '%s' is already in use", user.getUsername()));
        }

    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(String.format("User with id=%s not found ",id)));
    }

    @Transactional
    public User updatePassword(Long id, String oldPassword, String newPassword, String confirmNewPassword) {
        if (!newPassword.equals(confirmNewPassword)) {
            throw new PasswordInvalidException("The new password is different from the password confirmation");
        }

        User user = findById(id);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new PasswordInvalidException("Your password does not match");

        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with username=%s not found ",username)));

    }
    @Transactional(readOnly = true)
    public User.Role findRoleByUsername(String username) {
       return userRepository.findRoleByUsername(username);

    }
}
