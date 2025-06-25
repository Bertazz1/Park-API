package com.Bertazz1.demo_park_api.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResposeDto {

    private Long id;

    private String username;

    private String role;
}
