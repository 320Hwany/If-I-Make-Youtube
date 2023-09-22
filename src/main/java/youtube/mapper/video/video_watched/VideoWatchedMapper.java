package youtube.mapper.video.video_watched;

import youtube.domain.video.video_watched.persist.VideoWatched;
import youtube.mapper.video.video_watched.dto.VideoWatchedResponse;
import youtube.mapper.video.video_watched.dto.VideoWatchedResult;

import java.time.LocalDateTime;
import java.util.List;

public enum VideoWatchedMapper {

    VideoWatchedMapper() {
    };

    public static VideoWatched toEntity(final long memberId, final long videoInfoId,
                                        final LocalDateTime watchedDateTime) {

        return VideoWatched.builder()
                .memberId(memberId)
                .videoInfoId(videoInfoId)
                .watchedDate(watchedDateTime)
                .build();
    }

    public static VideoWatchedResult toResult(final long count, final List<VideoWatchedResponse> videoWatchedResponses) {
        return new VideoWatchedResult(count, videoWatchedResponses);
    }
}
