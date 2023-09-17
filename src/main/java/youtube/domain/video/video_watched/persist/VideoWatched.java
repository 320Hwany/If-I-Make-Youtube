package youtube.domain.video.video_watched.persist;

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
public class VideoWatched {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_watched_id")
    private Long id;

    @Association
    private Long memberId;

    @Association
    private Long videoInfoId;

    private LocalDateTime lastWatchedDate;

    @Builder
    private VideoWatched(final Long memberId, final Long videoInfoId, final LocalDateTime watchedDate) {
        this.memberId = memberId;
        this.videoInfoId = videoInfoId;
        this.lastWatchedDate = watchedDate;
    }
}
