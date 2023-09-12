package youtube.presentation.video.video_watch_later;

import org.springframework.web.bind.annotation.*;
import youtube.application.video.video_watch_later.command.CommandVideoWatchLaterSave;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;

@RequestMapping("/api")
@RestController
public class VideoWatchLaterController {

    private final CommandVideoWatchLaterSave commandVideoWatchLaterSave;

    public VideoWatchLaterController(final CommandVideoWatchLaterSave commandVideoWatchLaterSave) {
        this.commandVideoWatchLaterSave = commandVideoWatchLaterSave;
    }

    @PostMapping("/videoWatchLater/{videoInfoId}")
    public void saveVideoWatchLater(@Login final MemberSession memberSession,
                                    @PathVariable final long videoInfoId) {
        commandVideoWatchLaterSave.command(memberSession.id(), videoInfoId);
    }
}
