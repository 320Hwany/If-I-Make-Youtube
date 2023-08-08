package youtube.presentation.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import youtube.application.member.command.CommandPasswordUpdate;
import youtube.domain.member.vo.Password;
import youtube.util.ControllerTest;
import youtube.util.TestConstant;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.JwtConstant.ACCESS_TOKEN;

public class MemberUpdateControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인을 하지 않으면 비밀번호를 수정할 수 없습니다")
    void updatePasswordFail() throws Exception {
        // expected
        mockMvc.perform(patch("/api/member/password"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그인을 한 후 비밀번호를 수정할 수 있습니다")
    void updatePasswordSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();
        Password updatePassword = Password.from("수정 비밀번호!");

        // expected
        mockMvc.perform(patch("/api/member/password")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePassword))
                )
                .andExpect(status().isOk());
    }
}
