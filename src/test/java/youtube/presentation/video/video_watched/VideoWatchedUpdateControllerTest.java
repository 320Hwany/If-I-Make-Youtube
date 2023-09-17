package youtube.presentation.video.video_watched;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;

class VideoWatchedUpdateControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인을 하지 않으면 시청 기록을 업데이트 할 수 없습니다")
    void updateVideoWatchedFail() throws Exception {
        // given
        signup();

        // expected
        mockMvc.perform(post("/api/video-watched/{videoInfoId}", 1L)
                )
                .andExpect(status().isUnauthorized())
                .andDo(document("시청 기록 업데이트 실패 (로그인 하지 않음)",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("videoInfoId").description("동영상 정보 id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("동영상")
                                .summary("시청 기록 업데이트")
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인 후 시청 기록을 업데이트 할 수 있습니다")
    void updateVideoWatchedSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();

        // expected
        mockMvc.perform(post("/api/video-watched/{videoInfoId}", 1L)
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("시청 기록 업데이트",
                        resource(ResourceSnippetParameters.builder()
                                .tag("동영상")
                                .summary("시청 기록 업데이트")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .build()
                        )));
    }
}