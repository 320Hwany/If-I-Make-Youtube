package youtube.application.video.video_reaction.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.member.persist.Member;
import youtube.domain.video.video_reaction.vo.Reaction;
import youtube.mapper.video.video_reaction.dto.VideoReactionRequest;
import youtube.util.ServiceTest;

import static org.assertj.core.api.Assertions.*;

class VideoReactionCreatorTest extends ServiceTest {

    @Autowired
    private VideoReactionCreator videoReactionCreator;

    @Test
    @DisplayName("동영상에 대한 리액션 정보를 저장합니다")
    void commandVideoReaction() {
        // given
        Member member = saveMember();

        VideoReactionRequest dto = VideoReactionRequest.builder()
                .videoInfoId(1L)
                .originalReaction(Reaction.NO_REACTION)
                .updateReaction(Reaction.LIKE)
                .build();

        // when
        videoReactionCreator.command(member.getId(), dto);

        // then
        assertThat(videoReactionRepository.count()).isEqualTo(1);
    }
}