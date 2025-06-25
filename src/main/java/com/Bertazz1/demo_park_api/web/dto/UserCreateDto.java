package com.Bertazz1.demo_park_api.web.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserCreateDto {

    @NotBlank(message = "Username cannot be blank")
    @Email(message = "Invalid email format")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 6, message = "Password must be exactly 6 characters long")
    private String password;
}
