package youtube.application.jwt;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import youtube.util.AcceptanceTest;

import static org.assertj.core.api.Assertions.*;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;
import static youtube.global.constant.StringConstant.REFRESH_TOKEN;

@AcceptanceTest
class SetHeaderServiceTest {

    @Autowired
    private SetHeaderService setHeaderService;

    @Test
    @DisplayName("AccessToken을 Http 응답 헤더에 넣습니다")
    void setAccessTokenHeader() {
        // given
        MockHttpServletResponse response = new MockHttpServletResponse();
        String accessToken = ACCESS_TOKEN.value;

        // when
        setHeaderService.setAccessTokenHeader(response, accessToken);
        String accessTokenFromHeader = response.getHeader(ACCESS_TOKEN.value);

        // then
        assertThat(accessTokenFromHeader).isEqualTo(accessToken);
    }

    @Test
    @DisplayName("Http 응답 메세지의 쿠키에 RefreshToken을 넣습니다")
    void setRefreshTokenCookie() {
        // given
        MockHttpServletResponse response = new MockHttpServletResponse();
        String refreshToken = REFRESH_TOKEN.value;

        // when
        setHeaderService.setRefreshTokenCookie(response, refreshToken);
        Cookie cookie = response.getCookie(REFRESH_TOKEN.value);
        String value = cookie.getValue();

        // then
        assertThat(value).isEqualTo(refreshToken);
    }
}