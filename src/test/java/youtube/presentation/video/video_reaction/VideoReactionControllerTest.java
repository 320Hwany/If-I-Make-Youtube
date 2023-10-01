package youtube.presentation.video.video_reaction;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.domain.video.video_reaction.vo.Reaction;
import youtube.mapper.video.video_reaction.dto.VideoReactionRequest;
import youtube.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.global.constant.StringConstant.ACCESS_TOKEN;

class VideoReactionControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인을 하지 않으면 동영상에 리액션을 할 수 없습니다")
    void saveReactionFail401() throws Exception {
        // given
        VideoInfo videoInfo = saveVideoInfo();

        VideoReactionRequest dto = VideoReactionRequest.builder()
                .videoInfoId(videoInfo.getId())
                .originalReaction(Reaction.NO_REACTION)
                .updateReaction(Reaction.LIKE)
                .build();

        // expected
        mockMvc.perform(post("/api/v2/video-reaction")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized())
                .andDo(document("동영상의 리액션 저장 실패 - 401 (로그인 하지 않음)",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("동영상")
                                .summary("동영상 리액션")
                                .requestFields(
                                        fieldWithPath("videoInfoId").type(NUMBER).description("동영상 정보 id"),
                                        fieldWithPath("originalReaction").type(STRING).description("이전 리액션"),
                                        fieldWithPath("updateReaction").type(STRING).description("업데이트 리액션")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("닉네임"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인 후 동영상 정보 id에 맞는 리액션을 저장합니다")
    void saveReactionSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();

        VideoInfo videoInfo = saveVideoInfo();

        VideoReactionRequest dto = VideoReactionRequest.builder()
                .videoInfoId(videoInfo.getId())
                .originalReaction(Reaction.NO_REACTION)
                .updateReaction(Reaction.LIKE)
                .build();

        // expected
        mockMvc.perform(post("/api/v2/video-reaction")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(document("동영상의 리액션 저장 성공",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("동영상")
                                .summary("동영상 리액션")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("videoInfoId").type(NUMBER).description("동영상 정보 id"),
                                        fieldWithPath("originalReaction").type(STRING).description("이전 리액션"),
                                        fieldWithPath("updateReaction").type(STRING).description("업데이트 리액션")
                                )
                                .build()
                        )));
    }
}