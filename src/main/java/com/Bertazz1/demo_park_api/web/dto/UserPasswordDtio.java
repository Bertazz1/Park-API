package com.Bertazz1.demo_park_api.web.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPasswordDtio {

    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
