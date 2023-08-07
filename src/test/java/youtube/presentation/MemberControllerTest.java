package youtube.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.Gender;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.mapper.member.dto.MemberLoginRequest;
import youtube.mapper.member.dto.MemberSignupRequest;
import youtube.util.ControllerTest;

import java.time.LocalDate;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static youtube.global.constant.JwtConstant.*;
import static youtube.util.TestConstant.*;

class MemberControllerTest extends ControllerTest {

    @Test
    @DisplayName("회원가입시 닉네임, 아이디, 비밀번호를 입력하지 않으면 예외가 발생합니다")
    void signupFail() throws Exception {
        // given
        MemberSignupRequest dto = MemberSignupRequest.builder()
                .build();

        // expected
        mockMvc.perform(post("/api/signup")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입시 닉네임, 아이디, 비밀번호를 입력하고 조건에 맞으면 회원가입에 성공합니다")
    void signupSuccess() throws Exception {
        // given
        MemberSignupRequest dto = MemberSignupRequest.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .gender(Gender.MALE)
                .birthDate(LocalDate.now())
                .build();

        // expected
        mockMvc.perform(post("/api/signup")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

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

    @Test
    @DisplayName("로그인하지 않은 회원정보를 가져올 수 없습니다")
    void getMemberFail() throws Exception {
        // expected
        mockMvc.perform(get("/api/member")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그인 후 사용자 정보를 가져올 수 있습니다")
    void getMemberSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();

        // expected
        mockMvc.perform(get("/api/member")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}