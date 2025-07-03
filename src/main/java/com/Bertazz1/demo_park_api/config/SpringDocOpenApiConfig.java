package com.Bertazz1.demo_park_api.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("security", securityScheme()))
                .info(new Info()
                        .title("Rest API for Park Management")
                        .version("V1")
                        .description("API for managing park operations")
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"))
                        .contact(new Contact().name("Gustavo Bertuzzi")
                                .email("gustavobertuzzi10@gmail.com")));
    }

    private SecurityScheme securityScheme(){
        return new SecurityScheme()
                .description("Bearer Authentication")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("security");
    }
}
