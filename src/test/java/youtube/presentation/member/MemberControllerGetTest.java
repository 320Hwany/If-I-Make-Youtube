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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;

public class MemberControllerGetTest extends ControllerTest {

    @Test
    @DisplayName("로그인하지 않은 회원정보를 가져올 수 없습니다")
    void getMemberFail() throws Exception {
        // expected
        mockMvc.perform(get("/api/members"))
                .andExpect(status().isUnauthorized())
                .andDo(document("회원정보 가져오기 실패 (token) - 401 (로그인 상태가 아님)",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("회원정보 가져오기 (token)")
                                .responseFields(
                                        fieldWithPath("statusCode").description("닉네임"),
                                        fieldWithPath("message").description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인 후 사용자 정보를 가져올 수 있습니다")
    void getMemberSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();

        // expected
        mockMvc.perform(get("/api/members")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("회원정보 가져오기 성공 (token)",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("회원정보 가져오기 (token)")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 기본키"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("roleType").type(JsonFieldType.STRING).description("권한"),
                                        fieldWithPath("likedVideosCount").type(JsonFieldType.NUMBER)
                                                .description("좋아요 누른 동영상 수"),
                                        fieldWithPath("watchLaterVideosCount").type(JsonFieldType.NUMBER)
                                                .description("나중에 볼 동영상 수")
                                )
                                .build()
                        )));
    }
}
