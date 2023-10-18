package youtube.presentation.membership;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.domain.channel.persist.Channel;
import youtube.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;

class MembershipControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인을 하지 않으면 멤버십에 가입을 할 수 없습니다")
    void joinMembershipFail() throws Exception {
        // expected
        mockMvc.perform(post("/api/v2/membership/{channelId}", 9999L))
                .andExpect(status().isUnauthorized())
                .andDo(document("멤버십 가입 실패 - 401 (로그인 하지 않음)",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("멤버십")
                                .summary("멤버십 가입")
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("닉네임"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인을 한 후 멤버십에 가입할 수 있습니다")
    void joinMembershipSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();
        Channel channel = saveChannel();

        // expected
        mockMvc.perform(post("/api/v2/membership/{channelId}", channel.getId())
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("멤버십 가입 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("멤버십")
                                .summary("멤버십 가입")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .build()
                        )));
    }
}