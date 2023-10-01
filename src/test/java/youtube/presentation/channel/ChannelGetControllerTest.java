package youtube.presentation.channel;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.domain.channel.persist.Channel;
import youtube.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChannelGetControllerTest extends ControllerTest {

    @Test
    @DisplayName("채널 id와 일치하는 채널이 존재하지 않으면 예외가 발생합니다")
    void getChannelFail() throws Exception {
        mockMvc.perform(get("/api/v1/channel-cache/{channelId}", 9999))
                .andExpect(status().isNotFound())
                .andDo(document("채널을 찾을 수 없음 - 404",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("channelId").description("채널 ID")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("채널")
                                .summary("채널 찾기")
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("닉네임"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }


    @Test
    @DisplayName("채널 id로 채널 캐시 정보를 가져옵니다")
    void getChannelSuccess() throws Exception {
        // expected
        Channel channel = saveChannel();

        mockMvc.perform(get("/api/v1/channel-cache/{channelId}", channel.getId()))
                .andExpect(status().isOk())
                .andDo(document("채널 찾기 성공",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("channelId").description("채널 ID")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("채널")
                                .summary("채널 찾기")
                                .responseFields(
                                        fieldWithPath("channelName.channelName").type(STRING).description("채널 이름"),
                                        fieldWithPath("channelDescription.channelDescription").type(STRING)
                                                .description("채널 설명"),
                                        fieldWithPath("videosCount").type(NUMBER).description("채널의 동영상 수"),
                                        fieldWithPath("subscribersCount").type(NUMBER).description("구독자 수")
                                )
                                .build()
                        )));
    }
}
