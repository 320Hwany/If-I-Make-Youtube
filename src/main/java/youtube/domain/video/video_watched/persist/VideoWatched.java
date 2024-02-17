package youtube.domain.video.video_watched.persist;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import youtube.global.annotation.Association;

import java.time.LocalDateTime;

@Getter
@Table(name = "video_watched")
@Entity
public class VideoWatched {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_watched_id")
    private Long id;

    @Association
    private Long memberId;

    @Association
    private Long videoInfoId;

    private LocalDateTime lastWatchedDateTime;

    protected VideoWatched() {
    }

    @Builder
    private VideoWatched(final long memberId, final long videoInfoId, final LocalDateTime watchedDate) {
        this.memberId = memberId;
        this.videoInfoId = videoInfoId;
        this.lastWatchedDateTime = watchedDate;
    }

    public void updateLastWatchedDateTime(final LocalDateTime watchedDateTime) {
        this.lastWatchedDateTime = watchedDateTime;
    }
}
