package youtube.domain.video_info.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.domain.video_reaction.vo.Reaction;

import java.time.LocalDateTime;

import static youtube.global.constant.NumberConstant.ONE;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class VideoInfoCache {

    private String videoTitle;

    private String videoDescription;

    private long views;

    private long likesCount;

    private long dislikesCount;

    private LocalDateTime createdAt;

    @Builder
    private VideoInfoCache(final String videoTitle, final String videoDescription, final long views,
                          final long likesCount, final long dislikesCount, final LocalDateTime createdAt) {
        this.videoTitle = videoTitle;
        this.videoDescription = videoDescription;
        this.views = views;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.createdAt = createdAt;
    }

    public void updateReactionCount(final Reaction originalReaction, final Reaction updateReaction) {
        this.likesCount -= originalReaction.count;
        this.likesCount += updateReaction.count;

        this.dislikesCount -= originalReaction.count;
        this.dislikesCount += updateReaction.count;
    }
}
