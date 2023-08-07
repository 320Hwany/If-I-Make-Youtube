package youtube.application.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import youtube.domain.member.persist.MemberSession;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static youtube.global.constant.JwtKey.JWT_KEY;

@Service
public class CreateTokenService {

    private final ObjectMapper objectMapper;

    public CreateTokenService(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String createAccessToken(final MemberSession memberSession, final long expired) {
        Date now = new Date();
        Date expiredDate = new Date(new Date().getTime() + expired);
        SecretKey tokenKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JWT_KEY));

        try {
            String memberSessionJson = objectMapper.writeValueAsString(memberSession);
            return Jwts.builder()
                    .setId(UUID.randomUUID().toString())
                    .setSubject(memberSessionJson)
                    .setIssuedAt(now)
                    .setExpiration(expiredDate)
                    .signWith(tokenKey)
                    .compact();

        } catch (JsonProcessingException e) {
            // todo
            throw new RuntimeException(e);
        }
    }

    public String createRefreshToken(final long memberId, final long expired) {
        Date now = new Date();
        Date expiredDate = new Date(new Date().getTime() + expired);
        SecretKey tokenKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JWT_KEY));

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(String.valueOf(memberId))
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(tokenKey)
                .compact();
    }
}
