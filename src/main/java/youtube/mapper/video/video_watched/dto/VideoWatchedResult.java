package youtube.mapper.video.video_watched.dto;

import java.util.List;

public record VideoWatchedResult(
        long count,
        List<VideoWatchedResponse> videoWatchedResponses
) {

    public static VideoWatchedResult from(final long count, final List<VideoWatchedResponse> videoWatchedResponses) {
        return new VideoWatchedResult(count, videoWatchedResponses);
    }
}
