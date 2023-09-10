package youtube.application.video_reaction;

import org.springframework.stereotype.Service;
import youtube.application.video_reaction.command.CommandVideoReaction;
import youtube.mapper.video_reaction.dto.VideoReactionRequest;

@Service
public class VideoReactionUpdateService {

    private final CommandVideoReaction commandVideoReaction;
    private final VideoReactionCacheUpdateService videoReactionCacheUpdateService;

    public VideoReactionUpdateService(final CommandVideoReaction commandVideoReaction,
                                      final VideoReactionCacheUpdateService videoReactionCacheUpdateService) {
        this.commandVideoReaction = commandVideoReaction;
        this.videoReactionCacheUpdateService = videoReactionCacheUpdateService;
    }

    public void saveReaction(final long memberId, final VideoReactionRequest dto) {
        commandVideoReaction.command(memberId, dto);
        videoReactionCacheUpdateService.updateCache(dto);
    }
}
