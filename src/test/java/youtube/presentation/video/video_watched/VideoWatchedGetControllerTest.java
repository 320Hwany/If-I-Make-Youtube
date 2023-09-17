package youtube.presentation.video.video_watched;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;

class VideoWatchedGetControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인을 하지 않으면 시청 기록을 가져올 수 없습니다")
    void getVideoWatchedFail() throws Exception {
        // given
        signup();

        // expected
        mockMvc.perform(get("/api/video-watched"))
                .andExpect(status().isUnauthorized())
                .andDo(document("시청 기록 가져오기 실패 (로그인 하지 않음)",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("동영상")
                                .summary("시청 기록 가져오기")
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인 후 시청 기록을 가져올 수 있습니다")
    void getVideoWatchedSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();

        // expected
        mockMvc.perform(get("/api/video-watched")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("시청 기록 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("동영상")
                                .summary("시청 기록 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("count").type(NUMBER).description("동영상 수"),
                                        fieldWithPath("videoWatchedResponses").type(ARRAY).description("시청한 동영상")
                                )
                                .build()
                        )));
    }
}