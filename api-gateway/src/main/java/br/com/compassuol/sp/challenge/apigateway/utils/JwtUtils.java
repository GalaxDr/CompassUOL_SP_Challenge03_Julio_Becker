package br.com.compassuol.sp.challenge.apigateway.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import java.util.Calendar;
import java.util.Objects;

public class JwtUtils {

    public static String getTokenFromServerWebExchange(ServerWebExchange serverWebExchange) {
        String token = Objects.requireNonNull(serverWebExchange.getRequest()
                        .getHeaders()
                        .get(HttpHeaders.AUTHORIZATION))
                .get(0);

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7); // 7 primeiros caracteres -> "Bearer "
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Token");
    }

    public static Boolean tokenIsExpired(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt()
                    .before(Calendar.getInstance().getTime());

        } catch (JWTDecodeException e) {
            // Para mostrar a mensagem do segundo parametro eh necessario uma configuracao nas propriedades
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid JWT", e);
        }
    }
}
