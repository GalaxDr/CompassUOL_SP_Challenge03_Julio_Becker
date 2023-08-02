package br.com.compassuol.sp.challenge.apigateway;

import br.com.compassuol.sp.challenge.apigateway.utils.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class JwtTokenValidationFilter extends
        AbstractGatewayFilterFactory<JwtTokenValidationFilter.Config> {
    public JwtTokenValidationFilter() {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = new JwtUtils().getTokenFromServerWebExchange(exchange);
             if (! new JwtUtils().isTokenValid(token))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token");
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // ...
    }

}
