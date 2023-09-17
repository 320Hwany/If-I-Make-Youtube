package youtube.mapper.video.video_watched.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record VideoWatchedResponse(
        long videoInfoId,
        LocalDateTime lastWatchedDateTime
) {

    @QueryProjection
    public VideoWatchedResponse {
    }
}
