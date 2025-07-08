package com.Bertazz1.demo_park_api.repository.projection;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ClientSpaceProjection {
     String getClientCpf();
     String getLicensePlate();
     String getModel();
     String getBrand();
     String getColor();
     String getReceipt();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     LocalDateTime getEntryTime();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     LocalDateTime getExitTime();
     String getParkingSpaceCode();
     BigDecimal getPrice();
     BigDecimal getDiscount();

}
