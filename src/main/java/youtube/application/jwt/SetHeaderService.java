package youtube.application.jwt;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static youtube.global.constant.JwtConstant.ACCESS_TOKEN;
import static youtube.global.constant.JwtConstant.REFRESH_TOKEN;

@Service
public class SetHeaderService {

    private static final int THIRTY = 30;

    public void setAccessTokenHeader(final HttpServletResponse response, final String accessToken) {
        response.setHeader(ACCESS_TOKEN.value, accessToken);
    }

    public void setRefreshTokenCookie(final HttpServletResponse response, final String refreshToken) {
        ResponseCookie refreshTokenCookie = ResponseCookie.from(REFRESH_TOKEN.value, refreshToken)
                .maxAge(Duration.ofDays(THIRTY))
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }
}
