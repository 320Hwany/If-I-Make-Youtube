package youtube.presentation.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.util.ControllerTest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.JwtConstant.ACCESS_TOKEN;

public class MemberControllerGetDetailedTest extends ControllerTest {

    @Test
    @DisplayName("로그인하지 않은 회원정보를 가져올 수 없습니다")
    void getMemberDetailedFail() throws Exception {
        // expected
        mockMvc.perform(get("/api/member/detailed")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그인 후 사용자 정보를 가져올 수 있습니다")
    void getMemberDetailedSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();

        // expected
        mockMvc.perform(get("/api/member/detailed")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
