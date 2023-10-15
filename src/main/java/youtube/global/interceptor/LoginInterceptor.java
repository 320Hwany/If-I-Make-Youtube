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
import org.springframework.web.servlet.HandlerInterceptor;
import youtube.application.jwt.TokenFacade;
import youtube.domain.jwt.JwtRefreshToken;
import youtube.global.constant.TimeConstant;
import youtube.repository.jwt.JwtRepository;
import youtube.domain.member.persist.Member;
import youtube.repository.member.MemberRepository;
import youtube.domain.member.vo.MemberSession;
import youtube.global.exception.UnAuthorizedException;
import youtube.mapper.member.MemberMapper;

import java.util.Base64;

import static youtube.global.constant.ExceptionMessageConstant.*;
import static youtube.global.constant.StringConstant.*;
import static youtube.global.constant.JwtKey.JWT_KEY;
import static youtube.global.constant.TimeConstant.*;

public class LoginInterceptor implements HandlerInterceptor {

    private static final byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY);

    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final JwtRepository jwtRepository;
    private final TokenFacade tokenFacade;

    public LoginInterceptor(final ObjectMapper objectMapper,
                            final MemberRepository memberRepository,
                            final JwtRepository jwtRepository,
                            final TokenFacade tokenFacade) {
        this.objectMapper = objectMapper;
        this.memberRepository = memberRepository;
        this.jwtRepository = jwtRepository;
        this.tokenFacade = tokenFacade;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        String accessToken = request.getHeader(ACCESS_TOKEN.value);
        MemberSession memberSession = getMemberSessionFromToken(accessToken, request, response);
        request.setAttribute(MEMBER_SESSION.value, memberSession);
        return true;
    }

    private MemberSession getMemberSessionFromToken(final String accessToken,
                                                    final HttpServletRequest request,
                                                    final HttpServletResponse response) {
        // AccessToken payload에 MemberSession 객체 정보가 저장되어 있음 -> json 파싱 필요
        try {
            Jws<Claims> claims = getClaims(accessToken);
            String memberSessionJson = claims.getBody().getSubject();
            try {
                return objectMapper.readValue(memberSessionJson, MemberSession.class);
            } catch (JsonProcessingException e) {
                throw new UnAuthorizedException(ACCESS_TOKEN_JSON_PARSING.message);
            }

        } catch (JwtException e) {
            String refreshToken = getRefreshToken(request);
            return getMemberSessionFromRefreshToken(refreshToken, response);
        }
    }

    private Jws<Claims> getClaims(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(decodedKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (IllegalArgumentException e) {
            throw new UnAuthorizedException(CLAIMS_UNAUTHORIZED.message);
        }
    }

    private String getRefreshToken(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new UnAuthorizedException(COOKIE_NOT_EXIST.message);
        }

        for (Cookie cookie : cookies) {
            if (REFRESH_TOKEN.value.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        throw new UnAuthorizedException(REFRESH_TOKEN_NOT_EXIST.message);
    }

    private MemberSession getMemberSessionFromRefreshToken(final String refreshToken,
                                                           final HttpServletResponse response) {
        try {
            Jws<Claims> claims = getClaims(refreshToken);
            String memberId = claims.getBody().getSubject();
            JwtRefreshToken jwtRefreshToken = jwtRepository.getByMemberId(Long.parseLong(memberId));
            if (refreshToken.equals(jwtRefreshToken.getRefreshToken())) {
                Member member = memberRepository.getById(Long.parseLong(memberId));
                MemberSession memberSession = MemberMapper.toMemberSession(member);

                String accessToken = tokenFacade.createAccessToken(memberSession, ONE_HOUR.value);
                response.setHeader(ACCESS_TOKEN.value, accessToken);

                return memberSession;
            }

            throw new UnAuthorizedException(REFRESH_TOKEN_NOT_MATCH.message);
        } catch (JwtException e) {
            throw new UnAuthorizedException(REFRESH_TOKEN_NOT_VALID.message);
        }
    }
}
