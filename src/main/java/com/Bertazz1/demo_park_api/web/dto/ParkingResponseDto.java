package com.Bertazz1.demo_park_api.web.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParkingResponseDto {


    private String clientCpf;
    private String licensePlate;
    private String model;
    private String brand;
    private String color;
    private String receipt;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String parkingSpaceCode;
    private BigDecimal price;
    private BigDecimal discount;

}
