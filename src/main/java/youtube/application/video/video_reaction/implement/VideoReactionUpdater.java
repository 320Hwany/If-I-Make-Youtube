package youtube.application.video.video_reaction.implement;

import org.springframework.stereotype.Service;
import youtube.mapper.video.video_reaction.dto.VideoReactionRequest;

@Service
public class VideoReactionUpdater {

    private final VideoReactionCreator videoReactionCreator;
    private final VideoReactionCacheUpdater videoReactionCacheUpdater;

    public VideoReactionUpdater(final VideoReactionCreator videoReactionCreator,
                                final VideoReactionCacheUpdater videoReactionCacheUpdater) {
        this.videoReactionCreator = videoReactionCreator;
        this.videoReactionCacheUpdater = videoReactionCacheUpdater;
    }

    public void updateReaction(final long memberId, final VideoReactionRequest dto) {
        videoReactionCreator.command(memberId, dto);
        videoReactionCacheUpdater.updateCache(dto);
    }
}
