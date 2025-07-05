package com.Bertazz1.demo_park_api.web.controller;


import com.Bertazz1.demo_park_api.entity.ParkingSpace;
import com.Bertazz1.demo_park_api.service.ParkingSpaceService;
import com.Bertazz1.demo_park_api.web.dto.ClientResponseDto;
import com.Bertazz1.demo_park_api.web.dto.ParkingSpaceCreateDto;
import com.Bertazz1.demo_park_api.web.dto.ParkingSpaceResponseDto;
import com.Bertazz1.demo_park_api.web.dto.mapper.ParkingSpaceMapper;
import com.Bertazz1.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/parking-spaces")
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

    @Operation(summary = "Create a new parking space", description = "Creates a new parking space"+ "Acess is restricted to users with the ADMIN role.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201",
                           headers = @Header(name = HttpHeaders.LOCATION,description = "Parking space location"),
                            description = "Parking space created successfully"),
                    @ApiResponse(responseCode = "409",
                            description = "Parking space already exists",
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid ParkingSpaceCreateDto dto){
        ParkingSpace parkingSpace = ParkingSpaceMapper.toParkingSpace(dto);
        parkingSpaceService.createParkingSpace(parkingSpace);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(parkingSpace.getCode()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Find ParkingSpace by code", description = "Finds a parkingSpace by its code "+ "Acess is restricted to users with the ADMIN role.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "parkingSpace find successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ParkingSpaceResponseDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "parkingSpace not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            })
    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingSpaceResponseDto> getByCode(@PathVariable String code){
        ParkingSpace parkingSpace = parkingSpaceService.findByCode(code);
        return ResponseEntity.ok(ParkingSpaceMapper.toDto(parkingSpace));
    }


}
