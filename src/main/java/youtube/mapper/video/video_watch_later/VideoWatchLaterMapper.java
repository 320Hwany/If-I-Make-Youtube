package youtube.mapper.video.video_watch_later;

import youtube.domain.video.video_watch_later.VideoWatchLater;

import java.time.LocalDateTime;

public class VideoWatchLaterMapper {

    private VideoWatchLaterMapper() {
    }

    public static VideoWatchLater toEntity(final long memberId, final long videoInfoId) {
        return VideoWatchLater.builder()
                .memberId(memberId)
                .videoInfoId(videoInfoId)
                .watchLaterDate(LocalDateTime.now())
                .build();
    }
}
