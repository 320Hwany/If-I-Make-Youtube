package youtube.repository.video_watch_later;

import org.springframework.stereotype.Repository;

@Repository
public class VideoWatchLaterRepositoryImpl implements VideoWatchLaterRepository {

    private final VideoWatchLaterJpaRepository videoWatchLaterJpaRepository;

    public VideoWatchLaterRepositoryImpl(final VideoWatchLaterJpaRepository videoWatchLaterJpaRepository) {
        this.videoWatchLaterJpaRepository = videoWatchLaterJpaRepository;
    }
}
