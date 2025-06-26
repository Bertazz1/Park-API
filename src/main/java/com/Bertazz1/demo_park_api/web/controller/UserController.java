package com.Bertazz1.demo_park_api.web.controller;

import com.Bertazz1.demo_park_api.entity.User;
import com.Bertazz1.demo_park_api.service.UserService;
import com.Bertazz1.demo_park_api.web.dto.UserCreateDto;
import com.Bertazz1.demo_park_api.web.dto.UserPasswordDtio;
import com.Bertazz1.demo_park_api.web.dto.UserResposeDto;
import com.Bertazz1.demo_park_api.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResposeDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        User savedUser = userService.createUser(UserMapper.toUser(userCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(savedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResposeDto> getById(@Valid @PathVariable Long id) {
        User savedUser = userService.findById(id);
        return ResponseEntity.ok(UserMapper.toDto(savedUser));
    }

    @GetMapping
    public ResponseEntity<List<UserResposeDto>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(UserMapper.toListDto(users));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@Valid @PathVariable Long id,@RequestBody UserPasswordDtio dto) {
        User savedUser = userService.updatePassword(id, dto.getOldPassword(), dto.getNewPassword(), dto.getConfirmNewPassword());
        return ResponseEntity.noContent().build();
    }
}
