package youtube.application.video_reaction.command;

import org.springframework.stereotype.Service;
import youtube.domain.video_reaction.vo.Reaction;
import youtube.repository.video_reaction.VideoReactionRepository;

@Service
public class CommandVideoReaction {

    private final VideoReactionRepository videoReactionRepository;

    public CommandVideoReaction(final VideoReactionRepository videoReactionRepository) {
        this.videoReactionRepository = videoReactionRepository;
    }

    public void command(final long memberId, final long videoInfoId, Reaction reaction) {

    }
}
