package youtube.mapper.video.video_watched;

import youtube.domain.video.video_watched.persist.VideoWatched;

import java.time.LocalDateTime;

public class VideoWatchedMapper {

    private VideoWatchedMapper() {
    }

    public static VideoWatched toEntity(final long memberId, final long videoInfoId,
                                        final LocalDateTime watchedDateTime) {

        return VideoWatched.builder()
                .memberId(memberId)
                .videoInfoId(videoInfoId)
                .watchedDate(watchedDateTime)
                .build();
    }
}
