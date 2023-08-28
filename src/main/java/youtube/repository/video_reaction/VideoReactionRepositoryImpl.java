package youtube.repository.video_reaction;

import org.springframework.stereotype.Repository;

@Repository
public class VideoReactionRepositoryImpl implements VideoReactionRepository {

    private final VideoReactionJpaRepository videoReactionJpaRepository;

    public VideoReactionRepositoryImpl(final VideoReactionJpaRepository videoReactionJpaRepository) {
        this.videoReactionJpaRepository = videoReactionJpaRepository;
    }
}
