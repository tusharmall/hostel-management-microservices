package com.microservices.room_service.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(servers = { @Server(url = "/") })
public class OpenApiConfig {
    
}