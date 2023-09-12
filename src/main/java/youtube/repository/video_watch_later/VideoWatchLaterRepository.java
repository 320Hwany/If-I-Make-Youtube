package youtube.repository.video_watch_later;

import youtube.domain.video_watch_later.VideoWatchLater;

public interface VideoWatchLaterRepository {

    void save(final VideoWatchLater videoWatchLater);
}
