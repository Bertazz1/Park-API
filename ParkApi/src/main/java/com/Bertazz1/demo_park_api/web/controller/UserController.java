package com.Bertazz1.demo_park_api.web.controller;

import com.Bertazz1.demo_park_api.entity.User;
import com.Bertazz1.demo_park_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User savedUser = userService.findById(id);
        return ResponseEntity.ok(savedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id,@RequestBody User user) {
        User savedUser = userService.updatePassword(id, user.getPassword());
        return ResponseEntity.ok(savedUser);
    }
}
