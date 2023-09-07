package youtube.presentation.channel;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;
import youtube.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;

public class ChannelUpdateControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인하지 않은 회원은 채널명을 수정할 수 없습니다")
    void channelNameUpdateFail() throws Exception {
        // expected
        mockMvc.perform(patch("/api/channels/channelName")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ChannelName.from("수정 채널명"))))
                .andExpect(status().isUnauthorized())
                .andDo(document("채널명 수정 실패 - 401 (로그인 하지 않음)",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("채널")
                                .summary("채널명 수정")
                                .requestFields(
                                        fieldWithPath("channelName").type(STRING).description("채널명")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("닉네임"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인 후 사용자의 채널명을 변경할 수 있습니다")
    void channelNameUpdateSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();

        // expected
        mockMvc.perform(patch("/api/channels/channelName")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ChannelName.from("수정 채널명")))
                )
                .andExpect(status().isOk())
                .andDo(document("채널명 수정",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("채널")
                                .summary("채널명 수정")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("channelName").type(STRING).description("채널명")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인하지 않은 회원은 채널 설명을 수정할 수 없습니다")
    void channelDescriptionUpdateFail() throws Exception {
        // expected
        mockMvc.perform(patch("/api/channels/channelDescription")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ChannelDescription.from("수정 채널 설명"))))
                .andExpect(status().isUnauthorized())
                .andDo(document("채널 설명 수정 실패 - 401 (로그인 하지 않음)",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("채널")
                                .summary("채널 설명 수정")
                                .requestFields(
                                        fieldWithPath("channelDescription").type(STRING).description("채널 설명")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인 후 사용자의 채널 설명을 변경할 수 있습니다")
    void channelDescriptionUpdateSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();

        // expected
        mockMvc.perform(patch("/api/channels/channelDescription")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ChannelDescription.from("수정 채널 설명")))
                )
                .andExpect(status().isOk())
                .andDo(document("채널 설명 수정",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("채널")
                                .summary("채널 설명 수정")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("channelDescription").type(STRING).description("채널 설명")
                                )
                                .build()
                        )));
    }
}
