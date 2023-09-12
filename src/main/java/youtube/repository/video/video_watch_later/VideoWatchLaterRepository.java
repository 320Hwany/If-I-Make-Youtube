package youtube.repository.video.video_watch_later;

import youtube.domain.video.video_watch_later.VideoWatchLater;

public interface VideoWatchLaterRepository {

    void save(final VideoWatchLater videoWatchLater);
}
