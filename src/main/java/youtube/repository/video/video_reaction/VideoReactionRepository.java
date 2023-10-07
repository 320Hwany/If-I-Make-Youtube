package youtube.repository.video.video_reaction;

import youtube.domain.video.video_reaction.persist.VideoReaction;

import java.util.Optional;

public interface VideoReactionRepository {

    void save(final VideoReaction videoReaction);

    Optional<VideoReaction> findByMemberIdAndVideoInfoId(final long memberId, final long videoInfoId);

    long count();
}
