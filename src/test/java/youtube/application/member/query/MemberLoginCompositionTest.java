package youtube.application.member.query;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import youtube.application.member.composition.MemberLoginComposition;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Password;
import youtube.global.exception.BadRequestException;
import youtube.global.exception.NotFoundException;
import youtube.mapper.member.dto.MemberLoginRequest;
import youtube.util.ServiceTest;

import static org.assertj.core.api.Assertions.*;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;
import static youtube.global.constant.StringConstant.REFRESH_TOKEN;
import static youtube.util.TestConstant.*;

class MemberLoginCompositionTest extends ServiceTest {

    @Autowired
    private MemberLoginComposition memberLoginComposition;

    @Test
    @DisplayName("입력한 아이디와 일치하는 회원이 없으면 예외가 발생합니다")
    void loginFailNotFound() {
        // given
        MemberLoginRequest dto = new MemberLoginRequest(
                LoginId.from("로그인아이디"),
                Password.from("비밀번호123!")
        );

        MockHttpServletResponse response = new MockHttpServletResponse();

        // expected
        assertThatThrownBy(() -> memberLoginComposition.login(dto, response))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("로그인 시 비밀번호가 일치하지 않으면 예외가 발생합니다")
    void loginFailPasswordNotMatch() {
        // given
        saveMember();

        MemberLoginRequest dto = new MemberLoginRequest(
                LoginId.from(TEST_LOGIN_ID.value),
                Password.from("일치하지 않는 비밀번호!")
        );

        MockHttpServletResponse response = new MockHttpServletResponse();

        // expected
        assertThatThrownBy(() -> memberLoginComposition.login(dto, response))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("입력한 로그인 정보가 일치하면 로그인에 성공합니다")
    void loginSuccess() {
        // given 1
        saveMember();

        // given 2
        MemberLoginRequest dto = new MemberLoginRequest(
                LoginId.from(TEST_LOGIN_ID.value),
                Password.from(TEST_PASSWORD.value)
        );

        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        memberLoginComposition.login(dto, response);

        // then
        Cookie cookie = response.getCookie(REFRESH_TOKEN.value);
        assertThat(cookie).isNotNull();

        String accessToken = response.getHeader(ACCESS_TOKEN.value);
        assertThat(accessToken).isNotNull();
    }
}