package youtube.global.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import youtube.domain.jwt.persist.JwtRefreshToken;
import youtube.domain.jwt.persist.JwtRepository;
import youtube.domain.member.persist.Member;
import youtube.domain.member.persist.MemberRepository;
import youtube.domain.member.persist.MemberSession;
import youtube.mapper.member.MemberMapper;

import java.util.Base64;

import static youtube.global.constant.JwtConstant.MEMBER_SESSION;
import static youtube.global.constant.JwtConstant.REFRESH_TOKEN;
import static youtube.global.constant.JwtKey.JWT_KEY;

public class LoginInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY);

    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final JwtRepository jwtRepository;

    public LoginInterceptor(final ObjectMapper objectMapper,
                            final MemberRepository memberRepository,
                            final JwtRepository jwtRepository) {
        this.objectMapper = objectMapper;
        this.memberRepository = memberRepository;
        this.jwtRepository = jwtRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = request.getHeader(AUTHORIZATION);
        MemberSession memberSession = getMemberSessionFromAccessToken(accessToken, request);
        request.setAttribute(MEMBER_SESSION.value, memberSession);
        return true;
    }

    private MemberSession getMemberSessionFromAccessToken(final String accessToken,
                                                          final HttpServletRequest request) {
        try {
            Jws<Claims> claims = getClaims(accessToken);
            String memberSessionJson = claims.getBody().getSubject();
            try {
                return objectMapper.readValue(memberSessionJson, MemberSession.class);
            } catch (JsonProcessingException e) {
                // todo
                throw new RuntimeException(e);
            }

        } catch (JwtException e) {
            Cookie[] cookies = getCookies(request);
            String refreshToken = getRefreshToken(cookies);
            return getMemberSessionFromRefreshToken(refreshToken);
        }
    }

    private Jws<Claims> getClaims(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(decodedKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (IllegalArgumentException e) {
            // todo
            throw new IllegalStateException();
        }
    }

    private Cookie[] getCookies(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            // todo
            throw new RuntimeException();
        }
        return cookies;
    }

    private String getRefreshToken(final Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (REFRESH_TOKEN.value.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        // todo
        throw new RuntimeException();
    }

    private MemberSession getMemberSessionFromRefreshToken(final String refreshToken) {
        try {
            Jws<Claims> claims = getClaims(refreshToken);
            String memberId = claims.getBody().getSubject();
            JwtRefreshToken jwtRefreshToken = jwtRepository.getByMemberId(Long.parseLong(memberId));
            if (refreshToken.equals(jwtRefreshToken.getRefreshToken())) {
                Member member = memberRepository.getById(Long.parseLong(memberId));
                return MemberMapper.toMemberSession(member);
            }

            // todo
            throw new RuntimeException();
        } catch (JwtException e) {
            throw new RuntimeException();
        }
    }
}
