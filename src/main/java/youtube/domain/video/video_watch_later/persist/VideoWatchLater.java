package youtube.domain.video.video_watch_later.persist;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import youtube.global.annotation.Association;

import java.time.LocalDateTime;

@Getter
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

    protected VideoWatchLater() {
    }

    @Builder
    private VideoWatchLater(final Long memberId, final Long videoInfoId, final LocalDateTime watchLaterDate) {
        this.memberId = memberId;
        this.videoInfoId = videoInfoId;
        this.watchLaterDate = watchLaterDate;
    }
}
