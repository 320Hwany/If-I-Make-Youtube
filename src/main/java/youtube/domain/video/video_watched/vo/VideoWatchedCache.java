package youtube.domain.video.video_watched.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class VideoWatchedCache {

    private long videoInfoId;
    private LocalDateTime lastWatchedDate;

    @Builder
    private VideoWatchedCache(final long videoInfoId, final LocalDateTime lastWatchedDate) {
        this.videoInfoId = videoInfoId;
        this.lastWatchedDate = lastWatchedDate;
    }
}
