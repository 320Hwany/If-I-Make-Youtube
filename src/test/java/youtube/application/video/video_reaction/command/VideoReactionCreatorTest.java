package youtube.application.video.video_reaction.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.application.video.video_reaction.implement.VideoReactionCreator;
import youtube.domain.member.persist.Member;
import youtube.domain.video.video_reaction.persist.VideoReaction;
import youtube.domain.video.video_reaction.vo.Reaction;
import youtube.mapper.video.video_reaction.dto.VideoReactionRequest;
import youtube.util.ServiceTest;

import static org.assertj.core.api.Assertions.*;

class VideoReactionCreatorTest extends ServiceTest {

    @Autowired
    private VideoReactionCreator videoReactionCreator;

    @Test
    @DisplayName("동영상에 대한 리액션 정보를 저장합니다")
    void createVideoReaction() {
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

    @Test
    @DisplayName("동영상에 대한 리액션 정보를 업데이트합니다")
    void updateVideoReaction() {
        // given 1
        Member member = saveMember();

        VideoReaction videoReaction = VideoReaction.builder()
                .memberId(member.getId())
                .videoInfoId(1L)
                .build();

        videoReactionRepository.save(videoReaction);

        // given 2
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