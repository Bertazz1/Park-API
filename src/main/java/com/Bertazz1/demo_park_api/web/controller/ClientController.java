package com.Bertazz1.demo_park_api.web.controller;


import com.Bertazz1.demo_park_api.entity.Client;
import com.Bertazz1.demo_park_api.jwt.JwtUserDetails;
import com.Bertazz1.demo_park_api.service.ClientService;
import com.Bertazz1.demo_park_api.service.UserService;
import com.Bertazz1.demo_park_api.web.dto.ClientCreateDto;
import com.Bertazz1.demo_park_api.web.dto.ClientResponseDto;
import com.Bertazz1.demo_park_api.web.dto.mapper.ClientMapper;
import com.Bertazz1.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;


    @Operation(summary = "Create a new client", description = "Creates a new client with the provided details"+ "Acess is restricted to users with the CLIENT role.",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Client created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409",
                            description = "Client already exists",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422",
                            description = "Invalid input data",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403",
                            description = "Access denied",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> create(@RequestBody @Valid ClientCreateDto dto, @AuthenticationPrincipal JwtUserDetails userDetails) {
        Client client = ClientMapper.toClient(dto);
        client.setUser(userService.findById(userDetails.getId()));
        clientService.createClient(client);
        return ResponseEntity.status(201).body(ClientMapper.toDto(client));
    }
}
