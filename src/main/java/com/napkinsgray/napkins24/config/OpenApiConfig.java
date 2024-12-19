package com.napkinsgray.napkins24.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Napkins24 API 문서")
                        .version("1.0.0")
                        .description("Napkins24 프로젝트의 REST API 명세서입니다.")
                );
    }
}