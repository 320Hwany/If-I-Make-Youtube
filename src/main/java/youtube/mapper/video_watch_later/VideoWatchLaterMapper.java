package youtube.mapper.video_watch_later;

import youtube.domain.video_watch_later.VideoWatchLater;
import youtube.mapper.video_watch_later.dto.VideoWatchLaterSaveRequest;

import java.time.LocalDateTime;

public class VideoWatchLaterMapper {

    private VideoWatchLaterMapper() {
    }

    public static VideoWatchLater toEntity(final VideoWatchLaterSaveRequest dto) {
        return VideoWatchLater.builder()
                .memberId(dto.memberId())
                .videoInfoId(dto.videoInfoId())
                .watchLaterDate(LocalDateTime.now())
                .build();
    }
}
