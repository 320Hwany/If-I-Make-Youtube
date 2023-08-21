package youtube.application.member.query;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import youtube.application.member.MemberLoginService;
import youtube.domain.member.persist.Member;
import youtube.repository.member.MemberRepository;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.global.exception.BadRequestException;
import youtube.global.exception.NotFoundException;
import youtube.mapper.member.dto.MemberLoginRequest;
import youtube.util.AcceptanceTest;

import static org.assertj.core.api.Assertions.*;
import static youtube.global.constant.JwtConstant.ACCESS_TOKEN;
import static youtube.global.constant.JwtConstant.REFRESH_TOKEN;
import static youtube.util.TestConstant.*;

@AcceptanceTest
class MemberLoginServiceTest {

    @Autowired
    private MemberLoginService memberLoginService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        assertThatThrownBy(() -> memberLoginService.login(dto, response))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("로그인 시 비밀번호가 일치하지 않으면 예외가 발생합니다")
    void loginFailPasswordNotMatch() {
        // given
        memberRepository.save(Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .build());

        MemberLoginRequest dto = new MemberLoginRequest(
                LoginId.from(TEST_LOGIN_ID.value),
                Password.from("일치하지 않는 비밀번호!")
        );

        MockHttpServletResponse response = new MockHttpServletResponse();

        // expected
        assertThatThrownBy(() -> memberLoginService.login(dto, response))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("입력한 로그인 정보가 일치하면 로그인에 성공합니다")
    void loginSuccess() {
        // given
        Password password = Password.from(TEST_PASSWORD.value);

        memberRepository.save(Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(password.encode(passwordEncoder))
                .build());

        MemberLoginRequest dto = new MemberLoginRequest(
                LoginId.from(TEST_LOGIN_ID.value),
                Password.from(TEST_PASSWORD.value)
        );

        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        memberLoginService.login(dto, response);

        // then
        Cookie cookie = response.getCookie(REFRESH_TOKEN.value);
        assertThat(cookie).isNotNull();

        String accessToken = response.getHeader(ACCESS_TOKEN.value);
        assertThat(accessToken).isNotNull();
    }
}