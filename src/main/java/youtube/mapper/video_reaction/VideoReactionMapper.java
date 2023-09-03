package youtube.mapper.video_reaction;

import youtube.domain.video_reaction.persist.VideoReaction;
import youtube.mapper.video_reaction.dto.VideoReactionRequest;

public class VideoReactionMapper {

    private VideoReactionMapper() {
    }

    public static VideoReaction toEntity(final long memberId, final VideoReactionRequest dto) {
        return VideoReaction.builder()
                .memberId(memberId)
                .videoInfoId(dto.videoInfoId())
                .reaction(dto.reaction())
                .build();
    }
}
