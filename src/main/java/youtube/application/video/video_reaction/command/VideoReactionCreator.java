package youtube.application.video.video_reaction.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.video.video_reaction.persist.VideoReaction;
import youtube.mapper.video.video_reaction.VideoReactionMapper;
import youtube.mapper.video.video_reaction.dto.VideoReactionRequest;
import youtube.repository.video.video_reaction.VideoReactionRepository;

import java.util.Optional;

@Service
public class VideoReactionCreator {

    private final VideoReactionRepository videoReactionRepository;

    public VideoReactionCreator(final VideoReactionRepository videoReactionRepository) {
        this.videoReactionRepository = videoReactionRepository;
    }

    @Transactional
    public void command(final long memberId, final VideoReactionRequest dto) {
        Optional<VideoReaction> optionalVideoReaction =
                videoReactionRepository.findByMemberIdAndVideoInfoId(memberId, dto.videoInfoId());

        if (optionalVideoReaction.isPresent()) {
            VideoReaction psReaction = optionalVideoReaction.get();
            psReaction.updateReaction(dto.updateReaction());
        } else {
            VideoReaction entity = VideoReactionMapper.toEntity(memberId, dto);
            videoReactionRepository.save(entity);
        }
    }
}
