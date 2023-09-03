package youtube.mapper.video_reaction.dto;

import youtube.domain.video_reaction.vo.Reaction;

public record VideoReactionRequest(
        long videoInfoId,
        Reaction reaction
) {
}
