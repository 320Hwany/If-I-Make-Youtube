package youtube.presentation.subscription;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.util.ControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;

class SubscriptionCancelControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인을 하지 않으면 채널을 구독을 취소할 수 없습니다")
    void subscribeCancelUnauthorized() throws Exception {
        // expected
        mockMvc.perform(delete("/api/subscriptions"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("회원이 구독한 채널이 아니면 구독을 취소할 수 없습니다")
    void subscribeCancelFailNotFound() throws Exception {
        // given
        long channelId = signupAndSubscription();
        String accessToken = login();

        // expected
        mockMvc.perform(delete("/api/subscriptions")
                        .param("channelId", String.valueOf(9999))
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("로그인을 한 후 구독한 채널을 취소할 수 있습니다")
    void subscribeCancelSuccess() throws Exception {
        // given
        long channelId = signupAndSubscription();
        String accessToken = login();

        // expected
        mockMvc.perform(delete("/api/subscriptions")
                        .param("channelId", String.valueOf(channelId))
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk());
    }
}