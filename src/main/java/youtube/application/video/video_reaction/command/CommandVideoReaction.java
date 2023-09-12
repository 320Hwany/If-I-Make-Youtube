package youtube.application.video.video_reaction.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.video.video_reaction.persist.VideoReaction;
import youtube.mapper.video.video_reaction.VideoReactionMapper;
import youtube.mapper.video.video_reaction.dto.VideoReactionRequest;
import youtube.repository.video.video_reaction.VideoReactionRepository;

@Service
public class CommandVideoReaction {

    private final VideoReactionRepository videoReactionRepository;

    public CommandVideoReaction(final VideoReactionRepository videoReactionRepository) {
        this.videoReactionRepository = videoReactionRepository;
    }

    @Transactional
    public void command(final long memberId, final VideoReactionRequest dto) {
        VideoReaction entity = VideoReactionMapper.toEntity(memberId, dto);
        videoReactionRepository.save(entity);
    }
}
