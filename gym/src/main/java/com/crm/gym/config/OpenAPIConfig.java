package com.crm.gym.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class OpenAPIConfig
{
    @Bean
    public OpenAPI openAPI()
    {
        return new OpenAPI()
                .info(new Info()
                    .title("Gym CRM RESTful API")
                    .version("v1.0")
                    .description("RESTful API designed as a gym CRM solution, focused on managing trainers, trainees, workout sessions, and training categories.")
                    .contact(
                            new Contact()
                            .name("Levir Hernandez")
                            .url("https://www.linkedin.com/in/levir-heladio-hernandez-suarez-6916a6254")
                    )
                )
                .components(new Components()
                        .addSecuritySchemes(
                                "user_auth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")
                        )
                );
    }
}
