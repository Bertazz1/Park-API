package com.Bertazz1.demo_park_api;


import com.Bertazz1.demo_park_api.web.dto.UserCreateDto;
import com.Bertazz1.demo_park_api.web.dto.UserResposeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void testCreateUser_ReturnUserCreatedWithStatus201() {
       UserResposeDto userResposeDto = testClient.post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("testUser@gmail.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResposeDto.class)
                .returnResult().getResponseBody();

       org.assertj.core.api.Assertions.assertThat(userResposeDto).isNotNull();
       org.assertj.core.api.Assertions.assertThat(userResposeDto.getUsername()).isEqualTo("testUser@gmail.com");
       org.assertj.core.api.Assertions.assertThat(userResposeDto.getRole()).isEqualTo("CLIENT");
    }
}
