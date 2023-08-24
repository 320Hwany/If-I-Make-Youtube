package youtube.domain.video.persist;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.domain.BaseTimeEntity;
import youtube.domain.video.vo.VideoType;
import youtube.global.annotation.Association;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Video extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long id;

    @Association
    private Long channelId;

    private String videoTitle;

    @Enumerated(EnumType.STRING)
    private VideoType videoType;

    private long views;

    private long likesCount;

    private int contentCount;

    private String videoDescription;

    @Builder
    private Video(final Long channelId, final String videoTitle, final VideoType videoType, final long views,
                 final long likesCount, final int contentCount, final String videoDescription) {
        this.channelId = channelId;
        this.videoTitle = videoTitle;
        this.videoType = videoType;
        this.views = views;
        this.likesCount = likesCount;
        this.contentCount = contentCount;
        this.videoDescription = videoDescription;
    }
}
