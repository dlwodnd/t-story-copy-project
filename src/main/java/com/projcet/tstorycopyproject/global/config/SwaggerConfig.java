package com.projcet.tstorycopyproject.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(title = "T-Story Service ver.0.0.1"
                ,description = "티스토리 클론코딩 ver.0.0.1"
                ,version = "1.0.0")
        ,security = @SecurityRequirement(name = "authorization")
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP
        ,name = "authorization"
        ,in = SecuritySchemeIn.HEADER
        ,bearerFormat = "JWT"
        ,scheme = "Bearer"
)
public class SwaggerConfig {
}
