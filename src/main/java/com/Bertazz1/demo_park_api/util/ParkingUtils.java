package com.Bertazz1.demo_park_api.util;


import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ParkingUtils {

    public static String generateRecipt(){
        LocalDateTime date = LocalDateTime.now();
        String recipt = date.toString().substring(0,19);
        return recipt.replace("-", "")
        .replace(":", "")
        .replace("T", "-");
    }
}
