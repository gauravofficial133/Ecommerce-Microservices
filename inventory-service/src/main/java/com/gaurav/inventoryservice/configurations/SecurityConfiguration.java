package com.gaurav.inventoryservice.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        httpSecurity.authorizeRequests(authorize ->
                authorize.anyRequest()
                        .authenticated()
        ).oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter);
        httpSecurity.csrf().disable();
        return httpSecurity.build();
    }

}
