package youtube.presentation.subscription;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.domain.subscription.Subscription;
import youtube.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;

class SubscriptionCancelControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인을 하지 않으면 채널을 구독을 취소할 수 없습니다")
    void subscribeCancelUnauthorized() throws Exception {
        // expected
        mockMvc.perform(delete("/api/v2/subscriptions"))
                .andExpect(status().isUnauthorized())
                .andDo(document("구독 취소 실패 - 401 (로그인 하지 않음)",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("구독")
                                .summary("구독 취소")
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("닉네임"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("회원이 구독한 채널이 아니면 구독을 취소할 수 없습니다")
    void subscribeCancelFailNotFound() throws Exception {
        // given
        Subscription subscription = saveSubscription();
        String accessToken = login();

        // expected
        mockMvc.perform(delete("/api/v2/subscriptions")
                        .param("channelId", String.valueOf(9999))
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isNotFound())
                .andDo(document("구독 취소 실패 - 404 (구독한 채널이 아님)",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("구독")
                                .summary("구독 취소")
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("닉네임"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인을 한 후 구독한 채널을 취소할 수 있습니다")
    void subscribeCancelSuccess() throws Exception {
        // given
        Subscription subscription = saveSubscription();
        String accessToken = login();

        // expected
        mockMvc.perform(delete("/api/v2/subscriptions")
                        .param("channelId", String.valueOf(subscription.getChannelId()))
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("구독 취소 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("구독")
                                .summary("구독 취소")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .build()
                        )));
    }
}