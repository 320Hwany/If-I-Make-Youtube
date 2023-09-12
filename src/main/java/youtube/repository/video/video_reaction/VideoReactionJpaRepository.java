package youtube.repository.video.video_reaction;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.video.video_reaction.persist.VideoReaction;

import java.util.Optional;

public interface VideoReactionJpaRepository extends JpaRepository<VideoReaction, Long> {

    Optional<VideoReaction> findByMemberIdAndVideoInfoId(final long memberId, final long videoInfoId);
}
