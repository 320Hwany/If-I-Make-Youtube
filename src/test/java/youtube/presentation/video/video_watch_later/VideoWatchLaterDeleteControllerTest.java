package youtube.presentation.video.video_watch_later;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.domain.member.persist.Member;
import youtube.domain.video.video_watch_later.persist.VideoWatchLater;
import youtube.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;

public class VideoWatchLaterDeleteControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인을 하지 않으면 나중에 볼 동영상 삭제 실패합니다")
    void videoWatchLaterDeleteFailUnauthorized() throws Exception {
        // given 1
        Member member = saveMember();

        // given 2
        long videoInfoId = 1L;

        VideoWatchLater videoWatchLater = VideoWatchLater.builder()
                .memberId(member.getId())
                .videoInfoId(videoInfoId)
                .build();

        videoWatchLaterRepository.save(videoWatchLater);

        // expected
        mockMvc.perform(delete("/api/video-watch-later/{videoInfoId}", videoWatchLater.getId()))
                .andExpect(status().isUnauthorized())
                .andDo(document("나중에 볼 동영상 삭제 실패 - 로그인 하지 않음",
                        preprocessRequest(prettyPrint()),
                        pathParameters(
                                parameterWithName("videoInfoId").description("동영상 정보 id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("동영상")
                                .summary("나중에 볼 동영상")
                                .build()
                        )));
    }

    @Test
    @DisplayName("나중에 볼 동영상이 존재하지 않으면 삭제에 실패합니다")
    void videoWatchLaterDeleteFailNotFound() throws Exception {
        // given 1
        saveMember();
        String accessToken = login();

        // expected
        mockMvc.perform(delete("/api/video-watch-later/{videoInfoId}", 9999L)
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isNotFound())
                .andDo(document("나중에 볼 동영상 삭제 실패 - 동영상 찾을 수 없음",
                        preprocessRequest(prettyPrint()),
                        pathParameters(
                                parameterWithName("videoInfoId").description("동영상 정보 id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("동영상")
                                .summary("나중에 볼 동영상")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("나중에 볼 동영상 삭제에 성공합니다")
    void videoWatchLaterDeleteSuccess() throws Exception {
        // given 1
        Member member = saveMember();
        String accessToken = login();

        // given 2
        long videoInfoId = 1L;

        VideoWatchLater videoWatchLater = VideoWatchLater.builder()
                .memberId(member.getId())
                .videoInfoId(videoInfoId)
                .build();

        videoWatchLaterRepository.save(videoWatchLater);

        // expected
        mockMvc.perform(delete("/api/video-watch-later/{videoInfoId}", videoWatchLater.getId())
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("나중에 볼 동영상 삭제 성공",
                        preprocessRequest(prettyPrint()),
                        pathParameters(
                                parameterWithName("videoInfoId").description("동영상 정보 id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("동영상")
                                .summary("나중에 볼 동영상")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .build()
                        )));
    }
}
