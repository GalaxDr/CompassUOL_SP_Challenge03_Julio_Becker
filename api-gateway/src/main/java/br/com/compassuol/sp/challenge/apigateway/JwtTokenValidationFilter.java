package br.com.compassuol.sp.challenge.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static br.com.compassuol.sp.challenge.apigateway.utils.JwtUtils.getTokenFromServerWebExchange;
import static br.com.compassuol.sp.challenge.apigateway.utils.JwtUtils.tokenIsExpired;

@Component
public class JwtTokenValidationFilter extends
        AbstractGatewayFilterFactory<JwtTokenValidationFilter.Config> {

    public JwtTokenValidationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = getTokenFromServerWebExchange(exchange);
            if (tokenIsExpired(token))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expired");

            return chain.filter(exchange);
        };
    }

    public static class Config {
        // ...
    }

}
