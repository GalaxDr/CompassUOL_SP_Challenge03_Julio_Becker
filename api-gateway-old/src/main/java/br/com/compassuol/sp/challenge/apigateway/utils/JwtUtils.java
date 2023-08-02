package br.com.compassuol.sp.challenge.apigateway.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import java.util.Objects;

@Component
public class JwtUtils {
    public String getTokenFromServerWebExchange(ServerWebExchange serverWebExchange) {
        String token = Objects.requireNonNull(serverWebExchange.getRequest()
                        .getHeaders()
                        .get(HttpHeaders.AUTHORIZATION))
                .get(0);

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Token");
    }
    public boolean isTokenValid(String token) {
        try {
            String jwtSecret = "ed20c13a47e6becb1eaf82af2b75f7b1edf0facecca2421090eed861cacec57b";
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}