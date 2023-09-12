package youtube.mapper.video.video_reaction;

import youtube.domain.video.video_reaction.persist.VideoReaction;
import youtube.mapper.video.video_reaction.dto.VideoReactionRequest;

public class VideoReactionMapper {

    private VideoReactionMapper() {
    }

    public static VideoReaction toEntity(final long memberId, final VideoReactionRequest dto) {
        return VideoReaction.builder()
                .memberId(memberId)
                .videoInfoId(dto.videoInfoId())
                .reaction(dto.updateReaction())
                .build();
    }
}
