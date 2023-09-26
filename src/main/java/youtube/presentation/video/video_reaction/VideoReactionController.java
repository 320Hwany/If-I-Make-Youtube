package youtube.presentation.video.video_reaction;

import org.springframework.web.bind.annotation.*;
import youtube.application.video.video_reaction.VideoReactionUpdater;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;
import youtube.mapper.video.video_reaction.dto.VideoReactionRequest;

@RequestMapping("/api")
@RestController
public class VideoReactionController {

    private final VideoReactionUpdater videoReactionUpdater;

    public VideoReactionController(final VideoReactionUpdater videoReactionUpdater) {
        this.videoReactionUpdater = videoReactionUpdater;
    }

    @PostMapping("/video-reaction")
    public void videoReaction(@Login final MemberSession memberSession,
                              @RequestBody final VideoReactionRequest dto) {
        videoReactionUpdater.saveReaction(memberSession.id(), dto);
    }
}
