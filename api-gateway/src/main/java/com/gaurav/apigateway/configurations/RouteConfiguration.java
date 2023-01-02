package com.gaurav.apigateway.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

    @Autowired
    private TokenRelayGatewayFilterFactory tokenRelayGatewayFilterFactory;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/api/product/**")
                        .filters(f -> f
                                .filter(tokenRelayGatewayFilterFactory.apply())
                                        .removeRequestHeader("Cookie")
                        ).uri("lb:http://product-service"))

                .route(p -> p
                        .path("/api/order/**")
                        .filters(f -> f
                                .filter(tokenRelayGatewayFilterFactory.apply())
                                .removeRequestHeader("Cookie")
                        ).uri("lb:http://order-service"))

                .route(p -> p
                        .path("/api/inventory/**")
                        .filters(f -> f
                                .filter(tokenRelayGatewayFilterFactory.apply())
                                .removeRequestHeader("Cookie")
                        ).uri("lb:http://inventory-service"))

                .route(p -> p
                        .path("/eureka/web")
                        .filters(f -> f
                                .setPath("/")
                        ).uri("http://localhost:8761"))

                .route(p -> p
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))

                .build();
    }
}
