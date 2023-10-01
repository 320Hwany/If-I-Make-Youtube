package youtube.application.comment.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.comment.Comment;
import youtube.domain.member.vo.Nickname;
import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.mapper.comment.dto.CommentResponse;
import youtube.util.ServiceTest;
import youtube.util.TestConstant;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static youtube.util.TestConstant.*;

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
}