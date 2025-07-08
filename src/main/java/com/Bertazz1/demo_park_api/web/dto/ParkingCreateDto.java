package com.Bertazz1.demo_park_api.web.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ParkingCreateDto {



    @NotBlank
    @Size(min = 11,max = 11)
    @CPF
    private String clientCpf;
    @NotBlank
    @Size(min = 7,max = 7)
    @Pattern(regexp = "^[A-Z]{3}[0-9]{4}$", message = "License plate must be in the format 'AAA0000'")
    private String licensePlate;

    @NotBlank
    private String model;
    @NotBlank
    private String brand;
    @NotBlank
    private String color;

}
