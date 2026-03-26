package HoaThanhHai.quiz.security;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET ="mysecretkeymysecretkeymysecretkeymysecretkey";

    private Key getSignKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username, String role){
        return Jwts.builder()
        .setSubject(username)
        .claim("role", role)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+86400000))
        .signWith(getSignKey(), SignatureAlgorithm.HS256)
        .compact();
    }

    public String extractUserName(String token){
        return getClaims(token).getSubject();
    }

    public String extractRole(String token){
        return getClaims(token).get("role", String.class);
    }

    public boolean validateToken(String token){
        try{
            getClaims(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    private Claims getClaims(String token){
        return Jwts.parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }
}
