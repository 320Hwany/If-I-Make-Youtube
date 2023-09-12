package youtube.mapper.video.video_reaction.dto;

import lombok.Builder;
import youtube.domain.video.video_reaction.vo.Reaction;

@Builder
public record VideoReactionRequest(
        long videoInfoId,
        Reaction originalReaction,
        Reaction updateReaction
) {
}
