package youtube.domain.video.video_info.vo;

import lombok.Builder;
import lombok.Getter;
import youtube.domain.video.video_reaction.vo.Reaction;

import java.time.LocalDateTime;

import static youtube.global.constant.NumberConstant.*;

@Getter
public final class VideoInfoCache {

    private String videoTitle;

    private String videoDescription;

    private long views;

    private long likesCount;

    private long dislikesCount;

    private long commentCount;

    private LocalDateTime createdAt;

    @Builder
    private VideoInfoCache(final String videoTitle, final String videoDescription, final long views,
                          final long likesCount, final long dislikesCount,
                          final long commentCount, final LocalDateTime createdAt) {
        this.videoTitle = videoTitle;
        this.videoDescription = videoDescription;
        this.views = views;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
    }

    public void updateReactionCount(final Reaction originalReaction, final Reaction updateReaction) {
        this.likesCount += Reaction.updateLikesCount(originalReaction, updateReaction);
        this.dislikesCount += Reaction.updateDislikesCount(originalReaction, updateReaction);
    }

    public void plusOneCommentCount() {
        this.commentCount += ONE.value;
    }
}
