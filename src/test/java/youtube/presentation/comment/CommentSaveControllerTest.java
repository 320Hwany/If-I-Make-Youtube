package youtube.presentation.comment;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.mapper.comment.dto.CommentSaveRequest;
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

class CommentSaveControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인을 하지 않으면 댓글을 작성할 수 없습니다")
    void commentSaveUnauthorized() throws Exception {
        // expected
        mockMvc.perform(post("/api/v2/comments"))
                .andExpect(status().isUnauthorized())
                .andDo(document("댓글 작성 실패 - 401 (로그인 하지 않음)",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("댓글")
                                .summary("댓글 작성")
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("닉네임"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("댓글 내용이 비어있으면 댓글을 작성할 수 없습니다")
    void commentSaveNotValid() throws Exception {
        // given
        signup();
        String accessToken = login();

        CommentSaveRequest dto = CommentSaveRequest.builder()
                .videoInfoId(1L)
                .parentId(0L)
                .content("")
                .build();

        // expected
        mockMvc.perform(post("/api/v2/comments")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isBadRequest())
                .andDo(document("댓글 작성 실패 - 400 (댓글 내용 없음)",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("댓글")
                                .summary("댓글 작성")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("videoInfoId").type(NUMBER).description("동영상 정보 id"),
                                        fieldWithPath("parentId").type(NUMBER).description("부모 댓글 id"),
                                        fieldWithPath("content").type(STRING).description("댓글 내용")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("닉네임"),
                                        fieldWithPath("validation.content").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인을 한 후 원하는 댓글을 작성할 수 있습니다")
    void commentSaveSuccess() throws Exception {
        // given
        signup();
        String accessToken = login();
        VideoInfo videoInfo = saveVideoInfo();

        CommentSaveRequest dto = CommentSaveRequest.builder()
                .videoInfoId(videoInfo.getId())
                .parentId(0L)
                .content("댓글 내용입니다")
                .build();

        // expected
        mockMvc.perform(post("/api/v2/comments")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(document("구독 성공",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("구독")
                                .summary("구독")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("videoInfoId").type(NUMBER).description("동영상 정보 id"),
                                        fieldWithPath("parentId").type(NUMBER).description("부모 댓글 id"),
                                        fieldWithPath("content").type(STRING).description("댓글 내용")
                                )
                                .build()
                        )));
    }
}