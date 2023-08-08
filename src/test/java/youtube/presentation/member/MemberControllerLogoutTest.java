package youtube.presentation.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.util.ControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.JwtConstant.ACCESS_TOKEN;

public class MemberControllerLogoutTest extends ControllerTest {

    @Test
    @DisplayName("로그인을 하지 않으면 로그아웃을 할 수 없습니다")
    void logoutFail() throws Exception {
        // expected
        mockMvc.perform(post("/api/logout"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그인을 한 후 로그아웃을 진행할 수 있습니다")
    void logoutSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();

        // expected
        mockMvc.perform(post("/api/logout")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk());
    }
}
