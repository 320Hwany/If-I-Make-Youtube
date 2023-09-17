package youtube.mapper.video.video_watched.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record VideoWatchedCacheDto(
        long videoInfoId,
        LocalDateTime lastWatchedDate
) {

    @QueryProjection
    public VideoWatchedCacheDto {
    }
}
