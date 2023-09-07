package youtube.presentation.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.domain.member.vo.Password;
import youtube.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;

public class MemberUpdateControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인을 하지 않으면 비밀번호를 수정할 수 없습니다")
    void updatePasswordFail() throws Exception {
        // expected
        mockMvc.perform(patch("/api/members/password"))
                .andExpect(status().isUnauthorized())
                .andDo(document("비밀번호 수정 실패 - 401 (로그인 상태가 아님)",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("비밀번호 수정")
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("닉네임"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인을 한 후 비밀번호를 수정할 수 있습니다")
    void updatePasswordSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();
        Password updatePassword = Password.from("수정 비밀번호!");

        // expected
        mockMvc.perform(patch("/api/members/password")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePassword))
                )
                .andExpect(status().isOk())
                .andDo(document("비밀번호 수정 성공",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("비밀번호 수정")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("password").type(STRING).description("수정 비밀번호")
                                )
                                .build()
                        )));
    }
}
