package youtube.mapper.video.video_watched.dto;

import java.util.List;

public record VideoWatchedResult(
        long count,
        List<VideoWatchedResponse> videoWatchedResponses
) {
}
