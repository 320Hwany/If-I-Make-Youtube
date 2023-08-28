package youtube.domain.video_info.persist;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.domain.BaseTimeEntity;
import youtube.domain.video_info.vo.VideoType;
import youtube.global.annotation.Association;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private int contentCount;

    private String videoDescription;

    private String fileExtension;

    @Builder
    private VideoInfo(final Long channelId, final String videoTitle, final VideoType videoType, final long views,
                     final long likesCount, final long dislikesCount, final int contentCount,
                     final String videoDescription, final String fileExtension) {
        this.channelId = channelId;
        this.videoTitle = videoTitle;
        this.videoType = videoType;
        this.views = views;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.contentCount = contentCount;
        this.videoDescription = videoDescription;
        this.fileExtension = fileExtension;
    }
}
