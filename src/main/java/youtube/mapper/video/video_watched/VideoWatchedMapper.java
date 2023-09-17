package youtube.mapper.video.video_watched;

import youtube.domain.video.video_watched.persist.VideoWatched;
import youtube.domain.video.video_watched.vo.VideoWatchedCache;
import youtube.mapper.video.video_watched.dto.VideoWatchedCacheDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<VideoWatchedCache> toCaches(final List<VideoWatchedCacheDto> dtos) {
        return dtos
                .stream()
                .map(VideoWatchedMapper::toCache)
                .collect(Collectors.toList());
    }

    public static VideoWatchedCache toCache(final VideoWatchedCacheDto dto) {
        return VideoWatchedCache.builder()
                .videoInfoId(dto.videoInfoId())
                .lastWatchedDate(dto.lastWatchedDate())
                .build();
    }
}
