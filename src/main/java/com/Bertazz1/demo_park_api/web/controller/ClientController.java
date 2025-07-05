package com.Bertazz1.demo_park_api.web.controller;


import com.Bertazz1.demo_park_api.entity.Client;
import com.Bertazz1.demo_park_api.jwt.JwtUserDetails;
import com.Bertazz1.demo_park_api.repository.projection.ClientProjection;
import com.Bertazz1.demo_park_api.service.ClientService;
import com.Bertazz1.demo_park_api.service.UserService;
import com.Bertazz1.demo_park_api.web.dto.ClientCreateDto;
import com.Bertazz1.demo_park_api.web.dto.ClientResponseDto;
import com.Bertazz1.demo_park_api.web.dto.PageableDto;
import com.Bertazz1.demo_park_api.web.dto.mapper.ClientMapper;
import com.Bertazz1.demo_park_api.web.dto.mapper.PageableMapper;
import com.Bertazz1.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;


    @Operation(summary = "Create a new client", description = "Creates a new client with the provided details"+ "Acess is restricted to users with the CLIENT role.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201",
                            headers = @Header(name = HttpHeaders.LOCATION,description = "Client location"),
                            description = "Client created successfully"),
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

    @Operation(summary = "Find client by id", description = "Finds a client by their ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Client find successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClientResponseDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Client not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403",
                            description = "Access denied",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),

            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ClientResponseDto> findById(@PathVariable Long id) {
        Client client = clientService.findById(id);
        return ResponseEntity.ok(ClientMapper.toDto(client));
    }

    @Operation(summary = "Find all clients", description = "Finds all clients, acess is restricted to users with the ADMIN role.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Page number to retrieve"),
                    @Parameter(in = QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
                            description = "Number of items per page"),
                    @Parameter(in = QUERY, name = "sort", hidden = true,
                            content = @Content(schema = @Schema(type = "string", defaultValue = "id,asc")),
                            description = "Sorting criteria in the format: property,asc|desc. Default sort order is ascending."),

},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Clients find successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClientResponseDto.class))),

                    @ApiResponse(responseCode = "403",
                            description = "Access denied",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),

            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<PageableDto> findAll(@Parameter(hidden = true)@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        Page<ClientProjection> clients = clientService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(clients));
    }

    @Operation(summary = "Find client details by user authenticated", description = "Finds client details by user, acess is restricted to users with the CLIENT role.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Client find successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClientResponseDto.class))),
                    @ApiResponse(responseCode = "403",
                            description = "Access denied",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),

            })

    @GetMapping("/details")
    @PreAuthorize("hasRole('CLIENT')")
    public  ResponseEntity<ClientResponseDto> getDetails(@AuthenticationPrincipal JwtUserDetails userDetails){
       Client client = clientService.findByUserId(userDetails.getId());
        return ResponseEntity.ok(ClientMapper.toDto(client));
    }
}
