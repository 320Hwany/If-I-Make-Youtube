package youtube.application.jwt;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import youtube.application.jwt.implement.HeaderSetter;
import youtube.util.ServiceTest;

import static org.assertj.core.api.Assertions.*;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;
import static youtube.global.constant.StringConstant.REFRESH_TOKEN;

class HeaderSetterTest extends ServiceTest {

    @Autowired
    private HeaderSetter headerSetter;

    @Test
    @DisplayName("AccessToken을 Http 응답 헤더에 넣습니다")
    void setAccessTokenHeader() {
        // given
        MockHttpServletResponse response = new MockHttpServletResponse();
        String accessToken = ACCESS_TOKEN.value;

        // when
        headerSetter.setAccessTokenHeader(response, accessToken);
        String accessTokenFromHeader = response.getHeader(ACCESS_TOKEN.value);

        // then
        assertThat(accessTokenFromHeader).isEqualTo(accessToken);
    }

    @Test
    @DisplayName("Http 응답 헤더의 쿠키에 RefreshToken을 넣습니다")
    void setRefreshTokenCookie() {
        // given
        MockHttpServletResponse response = new MockHttpServletResponse();
        String refreshToken = REFRESH_TOKEN.value;

        // when
        headerSetter.setRefreshTokenCookie(response, refreshToken);
        Cookie cookie = response.getCookie(REFRESH_TOKEN.value);
        assert cookie != null;
        String value = cookie.getValue();

        // then
        assertThat(value).isEqualTo(refreshToken);
    }
}