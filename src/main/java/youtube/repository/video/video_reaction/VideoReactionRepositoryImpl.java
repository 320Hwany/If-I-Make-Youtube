package youtube.repository.video.video_reaction;

import org.springframework.stereotype.Repository;
import youtube.domain.video.video_reaction.persist.VideoReaction;

import java.util.Optional;


@Repository
public class VideoReactionRepositoryImpl implements VideoReactionRepository {

    private final VideoReactionJpaRepository videoReactionJpaRepository;

    public VideoReactionRepositoryImpl(final VideoReactionJpaRepository videoReactionJpaRepository) {
        this.videoReactionJpaRepository = videoReactionJpaRepository;
    }

    @Override
    public void save(final VideoReaction videoReaction) {
        videoReactionJpaRepository.save(videoReaction);
    }

    @Override
    public Optional<VideoReaction> findByMemberIdAndVideoInfoId(final long memberId, final long videoInfoId) {
        return videoReactionJpaRepository.findByMemberIdAndVideoInfoId(memberId, videoInfoId);
    }

    @Override
    public long count() {
        return videoReactionJpaRepository.count();
    }
}
