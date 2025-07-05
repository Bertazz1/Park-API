package com.Bertazz1.demo_park_api.web.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParkingSpaceResponseDto {

    private Long id;
    private String code;
    private String status;

}
