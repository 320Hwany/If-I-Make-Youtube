package youtube.presentation.subscription;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.util.ControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;

class SubscriptionControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인을 하지 않으면 채널을 구독을 할 수 없습니다")
    void subscribeFail() throws Exception {
        // expected
        mockMvc.perform(post("/api/subscription"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그인을 한 후 원하는 채널을 구독할 수 있습니다")
    void subscribeSuccess() throws Exception {
        // given
        long channelId = signupChannelId();
        String accessToken = login();

        // expected
        mockMvc.perform(post("/api/subscription")
                        .param("channelId", String.valueOf(channelId))
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk());
    }
}