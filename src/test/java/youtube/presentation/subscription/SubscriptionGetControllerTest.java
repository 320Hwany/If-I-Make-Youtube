package youtube.presentation.subscription;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;

public class SubscriptionGetControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인을 하지 않으면 구독한 채널 리스트를 가져올 수 없습니다")
    void getSubscriptionChannelsFail() throws Exception {
        // given
        saveSubscription();

        // expected
        mockMvc.perform(get("/api/v2/subscriptions")
                        .param("channelId", String.valueOf(9999))
                )
                .andExpect(status().isUnauthorized())
                .andDo(document("구독한 채널 리스트 조회 - 401 (로그인 하지 않음)",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("구독")
                                .summary("구독 리스트 조회")
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("닉네임"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인을 한 후 구독한 채널 리스트를 가져올 수 있습니다")
    void getSubscriptionChannelsSuccess() throws Exception {
        // given
        saveSubscription();
        String accessToken = login();

        // expected
        mockMvc.perform(get("/api/v2/subscriptions")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("구독한 채널 리스트 조회 성공",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("구독")
                                .summary("구독 리스트 조회")
                                .responseFields(
                                        fieldWithPath("subscriptionChannelsCaches[].channelId")
                                                .type(NUMBER).description("채널 id"),
                                        fieldWithPath("subscriptionChannelsCaches[].channelName.channelName")
                                                .type(STRING).description("채널 이름"),
                                        fieldWithPath("channelCount")
                                                .type(NUMBER).description("구독 채널 수")
                                )
                                .build()
                        )));
    }
}
