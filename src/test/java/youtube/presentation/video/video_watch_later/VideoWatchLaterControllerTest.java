package youtube.presentation.video.video_watch_later;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;

class VideoWatchLaterControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인 하지 않으면 나중에 볼 동영상을 저장에 실패합니다")
    void videoWatchLaterSaveFail() throws Exception {
        // given
        signup();

        // expected
        mockMvc.perform(post("/api/videoWatchLater/{videoInfoId}", 1L)
                )
                .andExpect(status().isUnauthorized())
                .andDo(document("나중에 볼 동영상 저장 실패",
                        pathParameters(
                                parameterWithName("videoInfoId").description("동영상 정보 id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("동영상")
                                .summary("나중에 볼 동영상 저장 실패")
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인 후 나중에 볼 동영상을 저장합니다")
    void videoWatchLaterSaveSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();

        // expected
        mockMvc.perform(post("/api/videoWatchLater/{videoInfoId}", 1L)
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("나중에 볼 동영상 저장 성공",
                        pathParameters(
                                parameterWithName("videoInfoId").description("동영상 정보 id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("동영상")
                                .summary("나중에 볼 동영상 저장")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .build()
                        )));
    }
}