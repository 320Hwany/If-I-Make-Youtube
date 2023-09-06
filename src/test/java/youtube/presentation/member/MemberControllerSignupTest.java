package youtube.presentation.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.domain.member.vo.Gender;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.mapper.member.dto.MemberSignupRequest;
import youtube.util.ControllerTest;

import java.time.LocalDate;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.util.TestConstant.*;

public class MemberControllerSignupTest extends ControllerTest {

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
                .andExpect(status().isBadRequest())
                .andDo(document("회원 가입 실패",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .description("회원 가입")
                                .responseFields(
                                        fieldWithPath("statusCode").description("닉네임"),
                                        fieldWithPath("message").description("오류 메세지")
                                )
                                .build(
                                ))));
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
                .andExpect(status().isOk())
                .andDo(document("회원 가입 성공",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("회원 가입")
                                .requestFields(
                                        fieldWithPath("nickname.nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("loginId.loginId").type(STRING).description("로그인 아이디"),
                                        fieldWithPath("password.password").type(STRING).description("비밀번호"),
                                        fieldWithPath("gender").type(STRING).description("성별"),
                                        fieldWithPath("birthDate").type(STRING).description("생년월일")
                                )
                                .build()
                        )));
    }
}
