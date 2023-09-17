package youtube.domain.video.video_watched.persist;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import youtube.global.annotation.Association;

import java.time.LocalDateTime;

@Getter
@Entity
public class VideoWatched {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_watched_id")
    private Long id;

    @Association
    private Long memberId;

    @Association
    private Long videoInfoId;

    private LocalDateTime lastWatchedDate;

    protected VideoWatched() {
    }

    @Builder
    private VideoWatched(final Long memberId, final Long videoInfoId, final LocalDateTime watchedDate) {
        this.memberId = memberId;
        this.videoInfoId = videoInfoId;
        this.lastWatchedDate = watchedDate;
    }
}
