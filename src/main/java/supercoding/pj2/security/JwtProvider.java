package supercoding.pj2.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

//    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Long userId, String email, String provider) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 1000 * 60 * 60 * 24); // 1일

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("email", email)
                .claim("provider", provider)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public String createToken(String email, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 1000 * 60 * 60 * 24); // 1일

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();

        return Long.parseLong(claims.getSubject()); // subject는 userId로 저장해둠
    }
}
