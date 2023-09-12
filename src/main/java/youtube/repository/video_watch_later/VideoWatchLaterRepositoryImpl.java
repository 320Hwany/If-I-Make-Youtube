package youtube.repository.video_watch_later;

import org.springframework.stereotype.Repository;
import youtube.domain.video_watch_later.VideoWatchLater;

@Repository
public class VideoWatchLaterRepositoryImpl implements VideoWatchLaterRepository {

    private final VideoWatchLaterJpaRepository videoWatchLaterJpaRepository;

    public VideoWatchLaterRepositoryImpl(final VideoWatchLaterJpaRepository videoWatchLaterJpaRepository) {
        this.videoWatchLaterJpaRepository = videoWatchLaterJpaRepository;
    }

    @Override
    public void save(final VideoWatchLater videoWatchLater) {
        videoWatchLaterJpaRepository.save(videoWatchLater);
    }
}
