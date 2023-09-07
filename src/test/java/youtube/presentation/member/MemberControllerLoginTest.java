package youtube.presentation.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.mapper.member.dto.MemberLoginRequest;
import youtube.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
                .andExpect(status().isNotFound())
                .andDo(document("로그인 실패 - 404 (아이디를 찾을 수 없음)",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("로그인")
                                .requestFields(
                                        fieldWithPath("loginId.loginId").type(STRING).description("로그인 아이디"),
                                        fieldWithPath("password.password").type(STRING).description("비밀번호")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("닉네임"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
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
                .andExpect(status().isBadRequest())
                .andDo(document("로그인 실패 - 400 (아이디 비밀번호 불일치)",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("로그인")
                                .requestFields(
                                        fieldWithPath("loginId.loginId").type(STRING).description("로그인 아이디"),
                                        fieldWithPath("password.password").type(STRING).description("비밀번호")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("닉네임"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인 아이디가 일치하면 로그인에 성공합니다")
    void loginSuccess() throws Exception {
        // given
        signup();

        MemberLoginRequest dto = new MemberLoginRequest(
                LoginId.from(TEST_LOGIN_ID.value),
                Password.from(TEST_PASSWORD.value)
        );

        // expected
        mockMvc.perform(post("/api/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(document("로그인 성공",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("로그인")
                                .requestFields(
                                        fieldWithPath("loginId.loginId").type(STRING).description("로그인 아이디"),
                                        fieldWithPath("password.password").type(STRING).description("비밀번호")
                                )
                                .responseFields(
                                        fieldWithPath("id").type(NUMBER).description("회원 기본키"),
                                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("loginId").type(STRING).description("로그인 아이디"),
                                        fieldWithPath("roleType").type(STRING).description("권한"),
                                        fieldWithPath("gender").type(STRING).description("성별"),
                                        fieldWithPath("birthDate").type(STRING).description("생년월일"),
                                        fieldWithPath("likedVideosCount").type(NUMBER).description("좋아요 누른 동영상 수"),
                                        fieldWithPath("watchLaterVideosCount").type(NUMBER)
                                                .description("나중에 볼 동영상 수")
                                )
                                .build()
                        )));
    }

}
