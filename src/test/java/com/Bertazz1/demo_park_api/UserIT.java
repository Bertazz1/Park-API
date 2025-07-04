package com.Bertazz1.demo_park_api;


import com.Bertazz1.demo_park_api.web.dto.UserCreateDto;
import com.Bertazz1.demo_park_api.web.dto.UserResponseDto;
import com.Bertazz1.demo_park_api.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;


import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void testCreateUser_ReturnUserCreatedWithStatus201() {
       UserResponseDto responseBody = testClient.post()
                .uri("/api/v1/users")
               .headers(JwtAuthentication.getHeaderAuthorization(testClient,"joao@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("testUser@gmail.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

       org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
       org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("testUser@gmail.com");
       org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");
    }
    @Test
    public void testCreateUser_TestEmailValidation_ReturnErrorMessageStatus422() {
        ErrorMessage responseBody = testClient.post()
                .uri("/api/v1/users")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"joao@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();

        responseBody = testClient.post()
                .uri("/api/v1/users")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"joao@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("testUser", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();

        responseBody = testClient.post()
                .uri("/api/v1/users")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"joao@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("testUser@gmail", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }
        @Test
        public void testCreateUser_TestPasswordValidation_ReturnErrorMessageStatus422() {
        ErrorMessage responseBody = testClient.post()
                .uri("/api/v1/users")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"joao@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("testUser@gmail.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();

        responseBody = testClient.post()
                .uri("/api/v1/users")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"joao@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("testUser@gmail.com", "123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();

        responseBody = testClient.post()
                .uri("/api/v1/users")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"joao@gmail.com","123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("testUser@gmail.com", "123456789"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }
    @Test
    public void findAllUsers_WithExistingId_ReturnUsers_WithStatus200() {
            List<UserResponseDto> responseBody = testClient
                .get()
                .uri("api/v1/users")
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient,"joao@gmail.com","123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserResponseDto.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void findUser_WithExistingId_ReturnUser_WithStatus200() {
            UserResponseDto responseBody = testClient
                .get()
                .uri("api/v1/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"joao@gmail.com","123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("joao@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");

        responseBody = testClient
                .get()
                .uri("api/v1/users/101")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"maria@gmail.com","123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(101);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("maria@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");

        responseBody = testClient
                .get()
                .uri("api/v1/users/102")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"pedro@gmail.com","123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(102);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("pedro@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");
    }


}
