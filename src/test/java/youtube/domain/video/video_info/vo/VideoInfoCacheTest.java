package youtube.domain.video.video_info.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.domain.video.video_reaction.vo.Reaction;

import static org.assertj.core.api.Assertions.*;

class VideoInfoCacheTest {

    @Test
    @DisplayName("싫어요 누른 동영상에 좋아요를 누르면 동영상 정보에 저장된 좋아요/싫어요 수가 변경됩니다")
    void updateReaction1() {
        // given
        VideoInfoCache cache = VideoInfoCache.builder()
                .likesCount(100)
                .dislikesCount(10)
                .build();

        Reaction originalReaction = Reaction.DISLIKE;
        Reaction updateReaction = Reaction.LIKE;

        // when
        cache.updateReactionCount(originalReaction, updateReaction);

        // then
        assertThat(cache.getLikesCount()).isEqualTo(101);
        assertThat(cache.getDislikesCount()).isEqualTo(9);
    }

    @Test
    @DisplayName("감정표현을 하지 않은 동영상에 좋아요를 누르면 동영상 정보에 저장된 좋아요 수가 변경됩니다")
    void updateReaction2() {
        // given
        VideoInfoCache cache = VideoInfoCache.builder()
                .likesCount(100)
                .dislikesCount(10)
                .build();

        Reaction originalReaction = Reaction.NO_REACTION;
        Reaction updateReaction = Reaction.LIKE;

        // when
        cache.updateReactionCount(originalReaction, updateReaction);

        // then
        assertThat(cache.getLikesCount()).isEqualTo(101);
        assertThat(cache.getDislikesCount()).isEqualTo(10);
    }

    @Test
    @DisplayName("싫어요를 누른 동영상 정보에 감정표현을 하지 않은 것으로 변경하면 저장된 싫어요 수가 변경됩니다")
    void updateReaction3() {
        // given
        VideoInfoCache cache = VideoInfoCache.builder()
                .likesCount(100)
                .dislikesCount(10)
                .build();

        Reaction originalReaction = Reaction.DISLIKE;
        Reaction updateReaction = Reaction.NO_REACTION;

        // when
        cache.updateReactionCount(originalReaction, updateReaction);

        // then
        assertThat(cache.getLikesCount()).isEqualTo(100);
        assertThat(cache.getDislikesCount()).isEqualTo(9);
    }
}