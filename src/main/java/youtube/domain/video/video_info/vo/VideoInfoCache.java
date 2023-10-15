package youtube.domain.video.video_info.vo;

import lombok.Builder;
import lombok.Getter;
import youtube.domain.video.video_reaction.vo.Reaction;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;


@Getter
public final class VideoInfoCache {

    private String videoTitle;

    private String videoDescription;

    private AtomicLong views;

    private AtomicLong likesCount;

    private AtomicLong dislikesCount;

    private AtomicLong commentCount;

    private LocalDateTime createdAt;

    @Builder
    private VideoInfoCache(final String videoTitle, final String videoDescription, final AtomicLong views,
                           final AtomicLong likesCount, final AtomicLong dislikesCount,
                           final AtomicLong commentCount, final LocalDateTime createdAt) {
        this.videoTitle = videoTitle;
        this.videoDescription = videoDescription;
        this.views = views;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
    }

    public void updateLikesCount(final Reaction originalReaction, final Reaction updateReaction) {
        if (originalReaction.value == Reaction.LIKE.value) {
            likesCount.decrementAndGet();
        }

        if (updateReaction.value == Reaction.LIKE.value) {
            likesCount.incrementAndGet();
        }
    }

    public void updateDisLikesCount(final Reaction originalReaction, final Reaction updateReaction) {
        if (originalReaction.value == Reaction.DISLIKE.value) {
            dislikesCount.decrementAndGet();
        }

        if (updateReaction.value == Reaction.DISLIKE.value) {
            dislikesCount.incrementAndGet();
        }
    }

    public void plusOneCommentCount() {
        commentCount.incrementAndGet();
    }
}
