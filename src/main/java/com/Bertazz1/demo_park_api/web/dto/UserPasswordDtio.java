package com.Bertazz1.demo_park_api.web.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPasswordDtio {

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 6, message = "Password must be exactly 6 characters long")
    private String oldPassword;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 6, message = "Password must be exactly 6 characters long")
    private String newPassword;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 6, message = "Password must be exactly 6 characters long")
    private String confirmNewPassword;
}
