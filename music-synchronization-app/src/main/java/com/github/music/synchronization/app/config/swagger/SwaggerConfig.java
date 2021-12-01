package com.github.music.synchronization.app.config.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springDemoOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Music Synchronization API")
                        .description("Music Synchronization API")
                        .version("v0.0.1")
                        .license(new License().name("project").url("localhost:8080")))
                .externalDocs(new ExternalDocumentation()
                        .description("Music Synchronization Documentation")
                        .url("localhost:8080"));
    }

    @Bean
    public GroupedOpenApi notificationServiceDocApi() {
        return GroupedOpenApi.builder()
                .group("music-synchronization-api")
                .pathsToMatch("/rest/**")
                .build();
    }


}
