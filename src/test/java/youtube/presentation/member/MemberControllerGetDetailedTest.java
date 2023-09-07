package youtube.presentation.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import youtube.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;

public class MemberControllerGetDetailedTest extends ControllerTest {

    @Test
    @DisplayName("로그인하지 않은 회원정보를 가져올 수 없습니다")
    void getMemberDetailedFail() throws Exception {
        // expected
        mockMvc.perform(get("/api/members/detailed"))
                .andExpect(status().isUnauthorized())
                .andDo(document("회원정보 가져오기 실패 (DB) - 401 (로그인 상태가 아님)",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("회원정보 가져오기 (DB)")
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("닉네임"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인 후 사용자 정보를 가져올 수 있습니다")
    void getMemberDetailedSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();

        // expected
        mockMvc.perform(get("/api/members/detailed")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("회원정보 가져오기 성공 (DB)",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("회원정보 가져오기 (DB)")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("id").type(NUMBER).description("회원 기본키"),
                                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("loginId").type(STRING).description("로그인 아이디"),
                                        fieldWithPath("roleType").type(STRING).description("권한"),
                                        fieldWithPath("gender").type(STRING).description("성별"),
                                        fieldWithPath("birthDate").type(STRING).description("생년월일"),
                                        fieldWithPath("likedVideosCount").type(NUMBER)
                                                .description("좋아요 누른 동영상 수"),
                                        fieldWithPath("watchLaterVideosCount").type(NUMBER)
                                                .description("나중에 볼 동영상 수")
                                )
                                .build()
                        )));
    }
}
