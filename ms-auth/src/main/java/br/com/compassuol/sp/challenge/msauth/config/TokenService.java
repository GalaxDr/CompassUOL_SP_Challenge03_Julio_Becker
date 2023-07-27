package br.com.compassuol.sp.challenge.msauth.config;

import br.com.compassuol.sp.challenge.msauth.entity.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
public class TokenService {
    @Value("${app.jwt-secret}")
    private String secret;
    @Value("${app.jwt-expiration-in-ms}")
    private String expiration;
    public String generateToken(User user) {
        try {
            Date currentDate = new Date();
            Date expiryDate = new Date(currentDate.getTime() + Long.parseLong(expiration));
            return Jwts.builder()
                    .setIssuer("CompassoUOL")
                    .setSubject(user.getUsername())
                    .setIssuedAt(currentDate)
                    .setExpiration(expiryDate)
                    .signWith(HS256, secret)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Error while generating token",e);
        }

    }
    public String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return "";
        }
    }
}
