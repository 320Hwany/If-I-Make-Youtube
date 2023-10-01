package youtube.presentation.comment;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import youtube.domain.comment.Comment;
import youtube.domain.member.vo.Nickname;
import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.mapper.comment.dto.CommentSaveRequest;
import youtube.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentGetControllerTest extends ControllerTest {

    @Test
    @DisplayName("동영상의 댓글을 좋아요 순으로 가져옵니다")
    void commentGetOrderByLikesCount() throws Exception {
        // given
        Comment comment1 = Comment.builder()
                .nickname(Nickname.from("닉네임 1"))
                .content("댓글 내용")
                .videoInfoId(9999L)
                .likesCount(9999L)
                .build();

        Comment comment2 = Comment.builder()
                .nickname(Nickname.from("닉네임 2"))
                .content("댓글 내용")
                .videoInfoId(9999L)
                .likesCount(0)
                .build();


        commentRepository.save(comment1);
        commentRepository.save(comment2);

        // expected
        mockMvc.perform(get("/api/v1/comments/{videoInfoId}/{page}", 9999L, 0))
                .andExpect(status().isOk())
                .andDo(document("댓글 가져오기 성공",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("댓글")
                                .summary("댓글 가져오기")
                                .responseFields(
                                        fieldWithPath("page")
                                                .type(NUMBER).description("페이지 수"),
                                        fieldWithPath("commentResponses[].nickname")
                                                .type(STRING).description("작성자 닉네임"),
                                        fieldWithPath("commentResponses[].content")
                                                .type(STRING).description("댓글 내용"),
                                        fieldWithPath("commentResponses[].childCommentCount")
                                                .type(NUMBER).description("대댓글 수"),
                                        fieldWithPath("commentResponses[].likesCount")
                                                .type(NUMBER).description("좋아요 수"),
                                        fieldWithPath("commentResponses[].createdAt")
                                                .type(STRING).description("댓글 작성 일자")
                                )
                                .build()
                        )));
    }
}
