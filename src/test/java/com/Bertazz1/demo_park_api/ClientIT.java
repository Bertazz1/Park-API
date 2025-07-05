package com.Bertazz1.demo_park_api;


import com.Bertazz1.demo_park_api.web.dto.ClientCreateDto;
import com.Bertazz1.demo_park_api.web.dto.ClientResponseDto;
import com.Bertazz1.demo_park_api.web.dto.PageableDto;
import com.Bertazz1.demo_park_api.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:sql/clients/clients-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/clients/clients-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void testCreateClient_ReturnClientCreatedWithStatus201() {
        ClientResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new ClientCreateDto("Tobyas Ferreia", "01890389935"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ClientResponseDto.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Tobyas Ferreia");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("01890389935");
    }

    @Test
    public void testCreateClient_CpfDuplicated_ReturnErrorStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new ClientCreateDto("Tobyas Ferreia", "87953162059"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void testCreateClient_InvalidInputData_ReturnErrorStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@gmail.com", "123456"))
                .bodyValue(new ClientCreateDto("Tobyas", "00000000000"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    responseBody = testClient
            .post()
            .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@gmail.com", "123456"))
            .bodyValue(new ClientCreateDto("Toby", "00000000000"))
            .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@gmail.com", "123456"))
                .bodyValue(new ClientCreateDto("Tobyas", "879.531.620-59"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void testCreateClient_UserNotAllowed_ReturnErrorStatus403() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new ClientCreateDto("Tobyas Ferreia", "87953162059"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void testFindClient_WithExistingIdByAdmin_ReturnClientWithStatus200() {
        ClientResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/clients/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientResponseDto.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(10);

    }
    @Test
    public void testFindClient_WithNoExistingIdByAdmin_ReturnErroWithStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/clients/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);



    }

    @Test
    public void testFindClient_WithExistingIdByClient_ReturnErrorWithStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/clients/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "pedro@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }
    @Test
    public void testFindAllClients_WithPage_ReturnClientsWithStatus200() {
        PageableDto responseBody = testClient
                .get()
                .uri("/api/v1/clients")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent()).size().isEqualTo(3);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(1);

        responseBody = testClient
                .get()
                .uri("/api/v1/clients?size=1&page=1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent()).size().isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(3);

    }

    @Test
    public void testFindAllClient_ReturnErrorWithStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/clients")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "pedro@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }

    @Test
    public void testFindClient_WithTokenClientData_ReturnClientWithStatus200() {
        ClientResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/clients/details")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "pedro@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientResponseDto.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("33374678068");
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo("12");


    }

    @Test
    public void testFindClient_WithTokenAdminData_ReturnClientWithStatus200() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/clients/details")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);



    }
}
