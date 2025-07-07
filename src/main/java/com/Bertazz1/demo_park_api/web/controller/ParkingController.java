package com.Bertazz1.demo_park_api.web.controller;


import com.Bertazz1.demo_park_api.entity.ClientSpace;
import com.Bertazz1.demo_park_api.service.ParkingService;
import com.Bertazz1.demo_park_api.web.dto.ParkingCreateDto;
import com.Bertazz1.demo_park_api.web.dto.ParkingResponseDto;
import com.Bertazz1.demo_park_api.web.dto.mapper.ClientSpaceMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {

    private final ParkingService parkingService;

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


}
