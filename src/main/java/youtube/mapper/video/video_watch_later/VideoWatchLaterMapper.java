package youtube.mapper.video.video_watch_later;

import youtube.domain.video.video_watch_later.persist.VideoWatchLater;

import java.time.LocalDateTime;

public enum VideoWatchLaterMapper {

    VideoWatchLaterMapper() {
    };

    public static VideoWatchLater toEntity(final long memberId, final long videoInfoId) {
        return VideoWatchLater.builder()
                .memberId(memberId)
                .videoInfoId(videoInfoId)
                .watchLaterDate(LocalDateTime.now())
                .build();
    }
}
