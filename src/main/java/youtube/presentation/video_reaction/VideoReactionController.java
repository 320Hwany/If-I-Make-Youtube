package youtube.presentation.video_reaction;

import org.springframework.web.bind.annotation.*;
import youtube.application.video_reaction.command.CommandVideoReaction;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;
import youtube.mapper.video_reaction.dto.VideoReactionRequest;

@RequestMapping("/api")
@RestController
public class VideoReactionController {

    private final CommandVideoReaction commandVideoReaction;

    public VideoReactionController(final CommandVideoReaction commandVideoReaction) {
        this.commandVideoReaction = commandVideoReaction;
    }

    @PostMapping("/videoReaction")
    public void videoReaction(@Login final MemberSession memberSession,
                              @RequestBody final VideoReactionRequest dto) {
        commandVideoReaction.command(memberSession.id(), dto);
    }
}
