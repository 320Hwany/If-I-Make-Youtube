package youtube.application.comment.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.global.constant.NumberConstant;
import youtube.mapper.comment.dto.CommentSaveRequest;
import youtube.util.ServiceTest;
import youtube.util.TestConstant;

import static org.assertj.core.api.Assertions.*;

class CommentCreatorTest extends ServiceTest {

    @Autowired
    protected CommentCreator commentCreator;

    @Test
    @DisplayName("로그인 한 회원이 동영상에 댓글을 작성합니다")
    void commandCommentSave() {
        // given
        VideoInfo videoInfo = VideoInfo.builder()
                .commentCount(NumberConstant.ZERO.value)
                .build();

        videoInfoRepository.save(videoInfo);

        CommentSaveRequest dto = CommentSaveRequest.builder()
                .videoInfoId(videoInfo.getId())
                .content("댓글 내용")
                .build();

        // when
        commentCreator.command(1L, dto);

        // then
        assertThat(commentRepository.count()).isEqualTo(1);
    }
}