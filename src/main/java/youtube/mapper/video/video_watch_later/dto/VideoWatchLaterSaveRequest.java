package youtube.mapper.video.video_watch_later.dto;

import lombok.Builder;

@Builder
public record VideoWatchLaterSaveRequest(
        long memberId,
        long videoInfoId
) {
}
