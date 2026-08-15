package com.microservices.course_service.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(servers = { @Server(url = "/") })
public class OpenApiConfig {

     @Bean
     public OpenAPI customOpenAPI() {
         return new OpenAPI()
             .components(new Components()
                 .addSecuritySchemes("bearerAuth",
                     new SecurityScheme()
                         .type(SecurityScheme.Type.HTTP)
                         .scheme("bearer")
                         .bearerFormat("JWT")))
             .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
             .info(new Info()
                 .title("Course Service API")
                 .version("1.0")
                 .description("REST APIs for Course Management Microservice")
                 .contact(new Contact().name("Tushar Mall").email("tusharmall1910@gmail.com")))
             .externalDocs(new ExternalDocumentation()
                 .description("Project Documentation")
                 .url("https://github.com/tusharmall/"));
     } 
}