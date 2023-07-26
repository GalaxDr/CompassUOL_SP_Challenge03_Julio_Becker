package br.com.compassuol.sp.challenge.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class GatewayConfig {

    @Value("${authentication-service-url}")
    private String authenticationServiceUrl;

    @Value("${products-service-url}")
    private String productsServiceUrl;

    @Value("${orders-service-url}")
    private String ordersServiceUrl;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**")
                        .uri(authenticationServiceUrl)) // Rota para o serviço de autenticação
                .route("products-service", r -> r.path("/products/**")
                        .uri(productsServiceUrl)) // Rota para o serviço de produtos
                .route("orders-service", r -> r.path("/orders/**")
                        .uri(ordersServiceUrl)) // Rota para o serviço de pedidos
                .build();
    }
}
