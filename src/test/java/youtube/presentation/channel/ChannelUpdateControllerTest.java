package youtube.presentation.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import youtube.domain.channel.vo.ChannelName;
import youtube.util.ControllerTest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.JwtConstant.ACCESS_TOKEN;

public class ChannelUpdateControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인하지 않은 회원은 채널명을 수정할 수 없습니다")
    void channelNameUpdateFail() throws Exception {
        // expected
        mockMvc.perform(patch("/api/channel/channelName")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ChannelName.from("수정 채널명"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그인 후 사용자의 채널명을 변경할 수 있습니다")
    void channelNameUpdateSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();

        // expected
        mockMvc.perform(patch("/api/channel/channelName")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ChannelName.from("수정 채널명")))
                )
                .andExpect(status().isOk());
    }
}
