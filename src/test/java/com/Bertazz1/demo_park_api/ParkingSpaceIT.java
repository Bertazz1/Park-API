package com.Bertazz1.demo_park_api;


import com.Bertazz1.demo_park_api.web.dto.ClientCreateDto;
import com.Bertazz1.demo_park_api.web.dto.ClientResponseDto;
import com.Bertazz1.demo_park_api.web.dto.ParkingSpaceCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:sql/parkingSpaces/parkingSpace-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/parkingSpaces/parkingSpace-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ParkingSpaceIT {


    @Autowired
    WebTestClient testClient;


    @Test
    public void createParkingSpace_WithValidData_ReturnLocationWithStatus201() {
        testClient.post()
                .uri("/api/v1/parking-spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@gmail.com", "123456"))
                .bodyValue(new ParkingSpaceCreateDto("A-05", "AVAILABLE"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);
    }

    @Test
    public void createParkingSpace_WithExistingCode_ReturnErrorWithStatus409() {
        testClient.post()
                .uri("/api/v1/parking-spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@gmail.com", "123456"))
                .bodyValue(new ParkingSpaceCreateDto("A-01", "AVAILABLE"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("status").isEqualTo(409)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parking-spaces");

    }

    @Test
    public void createParkingSpace_WithInvalidData_ReturnErrorWithStatus422() {
        testClient.post()
                .uri("/api/v1/parking-spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@gmail.com", "123456"))
                .bodyValue(new ParkingSpaceCreateDto("A-01", "UNAVAILABLE"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parking-spaces");

        testClient.post()
                .uri("/api/v1/parking-spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@gmail.com", "123456"))
                .bodyValue(new ParkingSpaceCreateDto("A-0111", "AVAILABLE"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parking-spaces");


    }

    @Test
    public void findParkingSpace_WithExistingCode_ReturnParkingSpaceWithStatus200() {
        testClient.get()
                .uri("/api/v1/parking-spaces/{code}", "A-01")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(10)
                .jsonPath("code").isEqualTo("A-01")
                .jsonPath("status").isEqualTo("AVAILABLE");

    }

    @Test
    public void findParkingSpace_WithNoExistingCode_ReturnErrorWithStatus404() {
        testClient.get()
                .uri("/api/v1/parking-spaces/{code}", "A-10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/parking-spaces/A-10");

    }
}
