package com.Bertazz1.demo_park_api.web.dto.mapper;


import com.Bertazz1.demo_park_api.entity.ParkingSpace;
import com.Bertazz1.demo_park_api.web.dto.ParkingSpaceCreateDto;
import com.Bertazz1.demo_park_api.web.dto.ParkingSpaceResponseDto;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ParkingSpaceMapper {

    public static ParkingSpace toParkingSpace(ParkingSpaceCreateDto dto) {
        return new ModelMapper().map(dto, ParkingSpace.class);
    }

    public static ParkingSpaceResponseDto toDto(ParkingSpace parkingSpace) {
        return new ModelMapper().map(parkingSpace, ParkingSpaceResponseDto.class);
    }
}
