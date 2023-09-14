package youtube.repository.video.video_watch_later;

import youtube.domain.video.video_watch_later.VideoWatchLater;


public interface VideoWatchLaterRepository {

    void save(final VideoWatchLater videoWatchLater);

    boolean existsByMemberIdAndVideoInfoId(final long memberId, final long videoInfoId);

    VideoWatchLater getByMemberIdAndVideoInfoId(final long memberId, final long videoInfoId);

    void delete(final VideoWatchLater videoWatchLater);

    long count();
}
