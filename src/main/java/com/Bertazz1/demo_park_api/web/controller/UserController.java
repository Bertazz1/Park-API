package com.Bertazz1.demo_park_api.web.controller;

import com.Bertazz1.demo_park_api.entity.User;
import com.Bertazz1.demo_park_api.service.UserService;
import com.Bertazz1.demo_park_api.web.dto.UserCreateDto;
import com.Bertazz1.demo_park_api.web.dto.UserPasswordDtio;
import com.Bertazz1.demo_park_api.web.dto.UserResposeDto;
import com.Bertazz1.demo_park_api.web.dto.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Management", description = "Operations related to user management")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;


    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details",
            responses = {
            @ApiResponse(responseCode = "201",
                    description = "User created successfully",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResposeDto.class))),
            @ApiResponse(responseCode = "409",
                    description = "Username already exists",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResposeDto.class))),
            @ApiResponse(responseCode = "422",
                    description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResposeDto.class)))
    })

    @PostMapping
    public ResponseEntity<UserResposeDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        User savedUser = userService.createUser(UserMapper.toUser(userCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(savedUser));
    }

    @Operation(summary = "Find user by id", description = "Finds a user by their ID",
            responses = {
            @ApiResponse(responseCode = "200",
                    description = "User find successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResposeDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResposeDto.class))),

    })

    @GetMapping("/{id}")
    public ResponseEntity<UserResposeDto> getById(@Valid @PathVariable Long id) {
        User savedUser = userService.findById(id);
        return ResponseEntity.ok(UserMapper.toDto(savedUser));
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Users retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResposeDto.class)))),
                    @ApiResponse(responseCode = "404",
                            description = "No users found",
                            content = @Content(mediaType = "application/json",
                                  schema = @Schema(implementation = UserResposeDto.class)))
            })

    @GetMapping
    public ResponseEntity<List<UserResposeDto>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(UserMapper.toListDto(users));
    }

    @Operation(summary = "Find user by id", description = "Finds a user by their ID",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "User password updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResposeDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "User not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResposeDto.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResposeDto.class)))

            })

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@Valid @PathVariable Long id,@RequestBody UserPasswordDtio dto) {
        User savedUser = userService.updatePassword(id, dto.getOldPassword(), dto.getNewPassword(), dto.getConfirmNewPassword());
        return ResponseEntity.noContent().build();
    }
}
