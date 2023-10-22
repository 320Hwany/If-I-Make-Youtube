package youtube.application.comment.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.application.comment.CommentReader;
import youtube.domain.comment.Comment;
import youtube.domain.member.vo.Nickname;
import youtube.mapper.comment.dto.CommentResponse;
import youtube.util.ServiceTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CommentReaderTest extends ServiceTest {

    @Autowired
    private CommentReader commentReader;

    @Test
    @DisplayName("댓글을 좋아요가 높은 순으로 한 페이지 가져옵니다")
    void findCommentResponsesOrderByLikes() {
        // given
        Comment comment1 = Comment.builder()
                .nickname(Nickname.from("닉네임 1"))
                .videoInfoId(9999L)
                .likesCount(9999L)
                .build();

        Comment comment2 = Comment.builder()
                .nickname(Nickname.from("닉네임 2"))
                .videoInfoId(9999L)
                .likesCount(0)
                .build();


        commentRepository.save(comment1);
        commentRepository.save(comment2);

        // when
        List<CommentResponse> commentResponses =
                commentReader.findCommentResponsesOrderByLikes(9999L, 0);

        // then
        assertThat(commentResponses.size()).isEqualTo(2);
        assertThat(commentResponses.get(0).likesCount()).isEqualTo(9999L);
        assertThat(commentResponses.get(1).likesCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("댓글을 최신순으로 한 페이지 가져옵니다")
    void findCommentResponsesOrderByLatest() {
        // given
        Comment comment1 = Comment.builder()
                .nickname(Nickname.from("닉네임 1"))
                .videoInfoId(9999L)
                .likesCount(9999L)
                .build();

        Comment comment2 = Comment.builder()
                .nickname(Nickname.from("닉네임 2"))
                .videoInfoId(9999L)
                .likesCount(0)
                .build();


        commentRepository.save(comment1);
        commentRepository.save(comment2);

        // when
        List<CommentResponse> commentResponses =
                commentReader.findCommentResponsesOrderByLatest(9999L, 0);

        // then
        assertThat(commentResponses.size()).isEqualTo(2);
        assertThat(commentResponses.get(0).nickname()).isEqualTo("닉네임 2");
        assertThat(commentResponses.get(1).nickname()).isEqualTo("닉네임 1");
    }
}