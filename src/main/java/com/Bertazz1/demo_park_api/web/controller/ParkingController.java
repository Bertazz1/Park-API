package com.Bertazz1.demo_park_api.web.controller;


import com.Bertazz1.demo_park_api.entity.ClientSpace;
import com.Bertazz1.demo_park_api.jwt.JwtUserDetails;
import com.Bertazz1.demo_park_api.repository.projection.ClientSpaceProjection;
import com.Bertazz1.demo_park_api.service.ClientSpaceService;
import com.Bertazz1.demo_park_api.service.ParkingService;
import com.Bertazz1.demo_park_api.web.dto.PageableDto;
import com.Bertazz1.demo_park_api.web.dto.ParkingCreateDto;
import com.Bertazz1.demo_park_api.web.dto.ParkingResponseDto;
import com.Bertazz1.demo_park_api.web.dto.mapper.ClientSpaceMapper;
import com.Bertazz1.demo_park_api.web.dto.mapper.PageableMapper;
import com.Bertazz1.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final ClientSpaceService clientSpaceService;

    @Operation(summary = "check-in operation", description = "Resource to register a vehicle in the parking lot",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201",
                            headers = @Header(name = HttpHeaders.LOCATION,description = "Parking space location"),
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ParkingResponseDto.class)),
                            description = "Registration successful"),
                    @ApiResponse(responseCode = "404",
                            description = "Possible errors: <br/>" +
                                    " - Client cpf not found, <br/>" +
                                    " - No avaliable parking space found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422",
                            description = "Invalid input data",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403",
                            description = "Access denied for role CLIENT",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))

            })
    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponseDto> checkIn(@RequestBody @Valid ParkingCreateDto dto) {
        ClientSpace clientSpace = ClientSpaceMapper.toClientSpace(dto);
        parkingService.checkIn(clientSpace);
        ParkingResponseDto responseDto = ClientSpaceMapper.toDto(clientSpace);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{receipt}")
                .buildAndExpand(clientSpace.getReceipt()).toUri();

        return ResponseEntity.created(location).body(responseDto);

    }

    @GetMapping("/check-in/{receipt}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<ParkingResponseDto> getByReceipt(@PathVariable String receipt) {
        ClientSpace clientSpace = clientSpaceService.findByReceipt(receipt);
        ParkingResponseDto responseDto = ClientSpaceMapper.toDto(clientSpace);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/check-out/{receipt}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponseDto> checkout(@PathVariable String receipt) {
        ClientSpace clientSpace = parkingService.checkOut(receipt);
        ParkingResponseDto responseDto = ClientSpaceMapper.toDto(clientSpace);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/cpf/{cpf}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAllParkingByCpf(@PathVariable String cpf,
                                                                 @PageableDefault(size = 5,sort = "entryTime",
                                                                         direction = Sort.Direction.ASC)Pageable pageable) {
        Page<ClientSpaceProjection> projection = clientSpaceService.findAllByCpf(cpf, pageable);
        PageableDto dto = PageableMapper.toDto(projection);
        return ResponseEntity.ok(dto);


    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAllClientParking(@AuthenticationPrincipal JwtUserDetails user,
                                                          @PageableDefault(size = 5,sort = "entryTime",
                                                                  direction = Sort.Direction.ASC)Pageable pageable) {
        Page<ClientSpaceProjection> projection = clientSpaceService.findAllByUserId(user.getId(), pageable);
        PageableDto dto = PageableMapper.toDto(projection);
        return ResponseEntity.ok(dto);


    }

}
