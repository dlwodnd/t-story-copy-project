package com.projcet.tstorycopyproject.global.auth.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Getter
@Configuration
@ConfigurationProperties(prefix = "app")
public class JwtProperties {
    private final Jwt jwt = new Jwt();
    private final Oauth2 oauth2 = new Oauth2();
    @Getter
    @Setter
    public class Jwt{
        private String secret;
        private String headerSchemeName;
        private String tokenType;
        private long accessTokenExpiry;
        private long refreshTokenExpiry;
        private int refreshTokenCookieMaxAge;
    }
    @Getter
    public static final class Oauth2 {
        private List<String> authorizedRedirectUris = new ArrayList();
    }
}
