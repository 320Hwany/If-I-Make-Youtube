package youtube.presentation.video.video_info;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VideoInfoControllerTest extends ControllerTest {

    @Test
    @DisplayName("동영상 정보 id와 일치하는 동영상 정보가 존재하지 않으면 예외가 발생합니다")
    void getVideoInfoFail404() throws Exception {
        // expected
        mockMvc.perform(get("/api/video-info-cache/{videoInfoId}", 9999))
                .andExpect(status().isNotFound())
                .andDo(document("동영상 정보를 찾을 수 없음 - 404",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("videoInfoId").description("동영상 정보 id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("동영상")
                                .summary("동영상 정보 찾기")
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("닉네임"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("동영상 정보 id로 동영상 캐시 정보를 가져옵니다")
    void getVideoInfoSuccess() throws Exception {
        // expected
        VideoInfo videoInfo = saveVideoInfo();

        mockMvc.perform(get("/api/video-info-cache/{videoInfoId}", videoInfo.getId()))
                .andExpect(status().isOk())
                .andDo(document("동영상 정보 찾기 성공",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("videoInfoId").description("동영상 정보 id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("동영상")
                                .summary("동영상 정보 찾기")
                                .responseFields(
                                        fieldWithPath("videoTitle").type(STRING).description("동영상 제목"),
                                        fieldWithPath("videoDescription").type(STRING).description("동영상 설명"),
                                        fieldWithPath("views").type(NUMBER).description("조회수"),
                                        fieldWithPath("likesCount").type(NUMBER).description("좋아요 수"),
                                        fieldWithPath("dislikesCount").type(NUMBER).description("싫어요 수"),
                                        fieldWithPath("commentCount").type(NUMBER).description("댓글 수"),
                                        fieldWithPath("createdAt").type(STRING).description("동영상 개시 일자")
                                )
                                .build()
                        )));
    }
}