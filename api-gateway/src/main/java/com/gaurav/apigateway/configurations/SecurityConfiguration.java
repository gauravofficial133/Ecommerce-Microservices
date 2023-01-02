package com.gaurav.apigateway.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/product/**").authenticated()
                        .pathMatchers("/api/order/**").authenticated()
                        .pathMatchers("/api/inventory/**").authenticated()
                        .pathMatchers("/eureka/**").permitAll()
        ).oauth2Login(Customizer.withDefaults());
        serverHttpSecurity.csrf().disable();
        return serverHttpSecurity.build();
    }


}


