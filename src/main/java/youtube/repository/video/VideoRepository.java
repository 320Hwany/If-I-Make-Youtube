package youtube.repository.video;

import youtube.domain.video.persist.Video;

public interface VideoRepository {

    void save(final Video video);

    long count();
}
