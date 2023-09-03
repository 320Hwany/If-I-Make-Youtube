package youtube.repository.video_reaction;

import youtube.domain.video_reaction.persist.VideoReaction;

public interface VideoReactionRepository {

    VideoReaction getByMemberIdAndVideoInfoId(final long memberId, final long videoInfoId);
}
