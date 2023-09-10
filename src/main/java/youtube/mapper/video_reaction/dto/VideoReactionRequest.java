package youtube.mapper.video_reaction.dto;

import lombok.Builder;
import youtube.domain.video_reaction.vo.Reaction;

@Builder
public record VideoReactionRequest(
        long videoInfoId,
        Reaction originalReaction,
        Reaction updateReaction
) {
}
