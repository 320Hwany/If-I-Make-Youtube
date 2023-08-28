package youtube.repository.video_reaction;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.video_reaction.persist.VideoReaction;

public interface VideoReactionJpaRepository extends JpaRepository<VideoReaction, Long> {
}
