package com.Bertazz1.demo_park_api;


import com.Bertazz1.demo_park_api.web.dto.ParkingCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:sql/parking/parking-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/parking/parking-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ParkingIT {

    @Autowired
    WebTestClient testClient;


    @Test
    public void createCheckIn_withValidData_ReturnCreateAndLocation() {
        ParkingCreateDto createDto = ParkingCreateDto.builder()
                .licensePlate("AAA0000").brand("FIAT").model("Palio")
                .color("Red").clientCpf("09191773016").build();

        testClient.post().uri("/api/v1/parking/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ana@email.com.br","123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody()
                .jsonPath("clientCpf").isEqualTo("09191773016")
                .jsonPath("licensePlate").isEqualTo("AAA0000")
                .jsonPath("model").isEqualTo("Palio")
                .jsonPath("brand").isEqualTo("FIAT")
                .jsonPath("color").isEqualTo("Red")
                .jsonPath("receipt").exists()
                .jsonPath("entryTime").exists()
                .jsonPath("parkingSpaceCode").exists();
    }

    @Test
    public void createCheckIn_withRoleClient_ReturnError403() {
        ParkingCreateDto createDto = ParkingCreateDto.builder()
                .licensePlate("AAA0000").brand("FIAT").model("Palio")
                .color("Red").clientCpf("09191773016").build();

        testClient.post().uri("/api/v1/parking/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"bia@email.com.br","123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/parking/check-in")
                .jsonPath("method").isEqualTo("POST");

    }

    @Test
    public void findCheckIn_withValidData_ReturnDataWithStatus200() {
        testClient.get()
                .uri("/api/v1/parking/check-in/{receipt}","20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ana@email.com.br","123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("clientCpf").isEqualTo("98401203015")
                .jsonPath("licensePlate").isEqualTo("FIT1020")
                .jsonPath("model").isEqualTo("PALIO")
                .jsonPath("brand").isEqualTo("FIAT")
                .jsonPath("color").isEqualTo("VERDE")
                .jsonPath("receipt").isEqualTo("20230313-101300")
                .jsonPath("entryTime").isEqualTo("2023-03-13 10:15:00")
                .jsonPath("parkingSpaceCode").exists();
    }
}
