package youtube.application.member.command;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import youtube.domain.member.persist.Member;
import youtube.domain.member.persist.MemberRepository;
import youtube.domain.member.persist.MemberSession;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.exception.member.LoginIdNotFoundException;
import youtube.exception.member.PasswordNotMatchException;
import youtube.mapper.member.dto.MemberLoginRequest;
import youtube.util.AcceptanceTest;

import static org.assertj.core.api.Assertions.*;
import static youtube.global.constant.SessionConstant.MEMBER_SESSION;
import static youtube.util.TestConstant.*;

@AcceptanceTest
class CommandMemberLoginTest {

    @Autowired
    private CommandMemberLogin commandMemberLogin;

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

        MockHttpServletRequest request = new MockHttpServletRequest();

        // expected
        assertThatThrownBy(() -> commandMemberLogin.command(dto, request))
                .isInstanceOf(LoginIdNotFoundException.class);
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

        MockHttpServletRequest request = new MockHttpServletRequest();

        // expected
        assertThatThrownBy(() -> commandMemberLogin.command(dto, request))
                .isInstanceOf(PasswordNotMatchException.class);
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

        MockHttpServletRequest request = new MockHttpServletRequest();

        // when
        commandMemberLogin.command(dto, request);

        // then
        HttpSession session = request.getSession(false);
        assertThat(session).isNotNull();

        MemberSession sessionAttribute = (MemberSession) session.getAttribute(MEMBER_SESSION.value);
        assertThat(sessionAttribute).isNotNull();
    }
}