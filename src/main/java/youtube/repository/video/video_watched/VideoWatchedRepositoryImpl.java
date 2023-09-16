package youtube.repository.video.video_watched;

import org.springframework.stereotype.Repository;

@Repository
public class VideoWatchedRepositoryImpl implements VideoWatchedRepository {

    private final VideoWatchedJpaRepository videoWatchedJpaRepository;

    public VideoWatchedRepositoryImpl(final VideoWatchedJpaRepository videoWatchedJpaRepository) {
        this.videoWatchedJpaRepository = videoWatchedJpaRepository;
    }
}
