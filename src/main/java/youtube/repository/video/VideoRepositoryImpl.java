package youtube.repository.video;

import org.springframework.stereotype.Repository;
import youtube.domain.video.persist.Video;

@Repository
public class VideoRepositoryImpl implements VideoRepository {

    private final VideoJpaRepository videoJpaRepository;

    public VideoRepositoryImpl(final VideoJpaRepository videoJpaRepository) {
        this.videoJpaRepository = videoJpaRepository;
    }

    @Override
    public void save(final Video video) {
        videoJpaRepository.save(video);
    }
}
