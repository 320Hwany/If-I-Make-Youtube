package youtube.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.mapper.member.dto.MemberLoginRequest;
import youtube.util.ControllerTest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.util.TestConstant.*;

public class MemberControllerLoginTest extends ControllerTest {

    @Test
    @DisplayName("로그인시 아이디가 일치하지 않으면 예외가 발생합니다")
    void loginFailNotFoundId() throws Exception {
        // given
        signup();

        MemberLoginRequest dto = new MemberLoginRequest(
                LoginId.from("InvalidLoginId"),
                Password.from(TEST_PASSWORD.value)
        );

        // expected
        mockMvc.perform(post("/api/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("로그인시 아이디가 일치하지 않으면 예외가 발생합니다")
    void loginFailNotMatchPassword() throws Exception {
        // given
        Password password = Password.from(TEST_PASSWORD.value);

        memberRepository.save(Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(password.encode(passwordEncoder))
                .build()
        );

        MemberLoginRequest dto = new MemberLoginRequest(
                LoginId.from(TEST_LOGIN_ID.value),
                Password.from("Password123!")
        );

        // expected
        mockMvc.perform(post("/api/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인 아이디가 일치하면 로그인에 성공합니다")
    void loginSuccess() throws Exception {
        // given
        Password password = Password.from(TEST_PASSWORD.value);

        memberRepository.save(Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(password.encode(passwordEncoder))
                .build()
        );

        MemberLoginRequest dto = new MemberLoginRequest(
                LoginId.from(TEST_LOGIN_ID.value),
                Password.from(TEST_PASSWORD.value)
        );

        // expected
        mockMvc.perform(post("/api/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

}
