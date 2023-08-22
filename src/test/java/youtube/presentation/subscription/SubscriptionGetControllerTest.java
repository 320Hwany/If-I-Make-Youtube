package youtube.presentation.subscription;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.util.ControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.JwtConstant.ACCESS_TOKEN;

public class SubscriptionGetControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인을 하지 않으면 구독한 채널 리스트를 가져올 수 없습니다")
    void getSubscriptionChannelsFail() throws Exception {
        // given
        signupAndSubscription();

        // expected
        mockMvc.perform(delete("/api/subscription")
                        .param("channelId", String.valueOf(9999))
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그인을 한 후 구독한 채널 리스트를 가져올 수 있습니다")
    void getSubscriptionChannelsSuccess() throws Exception {
        // given
        signupAndSubscription();
        String accessToken = login();

        // expected
        mockMvc.perform(get("/api/subscription")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk());
    }
}
