package youtube.repository.video.video_reaction;

import youtube.domain.video.video_reaction.persist.VideoReaction;

public interface VideoReactionRepository {

    void save(final VideoReaction videoReaction);

    VideoReaction getByMemberIdAndVideoInfoId(final long memberId, final long videoInfoId);

    long count();
}
