package youtube.domain.video.video_info.vo;

import lombok.Builder;
import lombok.Getter;
import youtube.domain.video.video_reaction.vo.Reaction;

import java.time.LocalDateTime;

@Getter
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
