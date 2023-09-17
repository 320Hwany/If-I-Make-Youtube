package youtube.repository.video.video_watched;

import youtube.domain.video.video_watched.persist.VideoWatched;
import youtube.mapper.video.video_watched.dto.VideoWatchedCacheDto;

import java.util.List;

public interface VideoWatchedRepository {

    void save(final VideoWatched videoWatched);

    List<VideoWatchedCacheDto> getVideoWatchedCacheDtosByMemberId(final long memberId);

    long count();
}
