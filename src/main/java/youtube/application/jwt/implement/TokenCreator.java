package youtube.application.jwt.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import youtube.domain.member.vo.MemberSession;
import youtube.global.exception.BadRequestException;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static youtube.global.constant.ExceptionMessageConstant.MEMBER_SESSION_JSON_PARSING;
import static youtube.global.constant.JwtKey.JWT_KEY;

@Service
public class TokenCreator {

    private final ObjectMapper objectMapper;

    public TokenCreator(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // MemberSession 객체 정보를 AccessToken에 넣습니다
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
            throw new BadRequestException(MEMBER_SESSION_JSON_PARSING.message);
        }
    }

    // MemberId를 RefreshToken에 넣습니다
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
