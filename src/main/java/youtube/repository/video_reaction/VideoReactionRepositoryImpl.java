package youtube.repository.video_reaction;

import org.springframework.stereotype.Repository;
import youtube.domain.video_reaction.persist.VideoReaction;
import youtube.global.exception.BadRequestException;

import static youtube.global.constant.ExceptionMessageConstant.*;

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
    public VideoReaction getByMemberIdAndVideoInfoId(final long memberId, final long videoInfoId) {
        return videoReactionJpaRepository.findByMemberIdAndVideoInfoId(memberId, videoInfoId)
                .orElseThrow(() -> new BadRequestException(VIDEO_REACTION_NOT_FOUND.message));
    }

    @Override
    public long count() {
        return videoReactionJpaRepository.count();
    }
}
