package youtube.presentation.channel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.util.ControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChannelGetControllerTest extends ControllerTest {

    @Test
    @DisplayName("채널 id와 일치하는 채널이 존재하지 않으면 예외가 발생합니다")
    void getChannelFail() throws Exception {
        // expected
        mockMvc.perform(get("/api/channelCache/{channelId}", 9999))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("채널 id로 채널 캐시 정보를 가져옵니다")
    void getChannelSuccess() throws Exception {
        // expected
        long channelId = signupChannelId();

        mockMvc.perform(get("/api/channelCache/{channelId}", channelId))
                .andExpect(status().isOk());
    }
}
