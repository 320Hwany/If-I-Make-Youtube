package youtube.domain.video.video_watch_later;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.global.annotation.Association;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class VideoWatchLater {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_watch_later_id")
    private Long id;

    @Association
    private Long memberId;

    @Association
    private Long videoInfoId;

    private LocalDateTime watchLaterDate;

    @Builder
    private VideoWatchLater(final Long memberId, final Long videoInfoId, final LocalDateTime watchLaterDate) {
        this.memberId = memberId;
        this.videoInfoId = videoInfoId;
        this.watchLaterDate = watchLaterDate;
    }
}
