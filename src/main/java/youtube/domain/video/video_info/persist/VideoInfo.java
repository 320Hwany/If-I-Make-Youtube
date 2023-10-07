package youtube.domain.video.video_info.persist;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import youtube.domain.BaseTimeEntity;
import youtube.domain.video.video_info.vo.VideoInfoCache;
import youtube.domain.video.video_info.vo.VideoType;
import youtube.global.annotation.Association;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Entity
public class VideoInfo extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_info_id")
    private Long id;

    @Association
    private Long channelId;

    private String videoTitle;

    @Enumerated(EnumType.STRING)
    private VideoType videoType;

    private long views;

    private long likesCount;

    private long dislikesCount;

    private long commentCount;

    private String videoDescription;

    private String fileExtension;

    protected VideoInfo() {
    }

    @Builder
    private VideoInfo(final long channelId, final String videoTitle, final VideoType videoType, final long views,
                     final long likesCount, final long dislikesCount, final int commentCount,
                     final String videoDescription, final String fileExtension) {
        this.channelId = channelId;
        this.videoTitle = videoTitle;
        this.videoType = videoType;
        this.views = views;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.commentCount = commentCount;
        this.videoDescription = videoDescription;
        this.fileExtension = fileExtension;
    }

    public void updateCacheInfo(final VideoInfoCache cache) {
        AtomicLong atomicCommentCount = cache.getCommentCount();

        this.videoTitle = cache.getVideoTitle();
        this.videoDescription = cache.getVideoDescription();
        this.views = cache.getViews();
        this.likesCount = cache.getLikesCount();
        this.dislikesCount = cache.getDislikesCount();
        this.commentCount = atomicCommentCount.get();
    }
}
