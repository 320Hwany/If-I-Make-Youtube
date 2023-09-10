package youtube.presentation.video_reaction;

import org.springframework.web.bind.annotation.*;
import youtube.application.video_reaction.VideoReactionUpdateService;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;
import youtube.mapper.video_reaction.dto.VideoReactionRequest;

@RequestMapping("/api")
@RestController
public class VideoReactionController {

    private final VideoReactionUpdateService videoReactionUpdateService;

    public VideoReactionController(final VideoReactionUpdateService videoReactionUpdateService) {
        this.videoReactionUpdateService = videoReactionUpdateService;
    }

    @PostMapping("/videoReaction")
    public void videoReaction(@Login final MemberSession memberSession,
                              @RequestBody final VideoReactionRequest dto) {
        videoReactionUpdateService.saveReaction(memberSession.id(), dto);
    }
}
