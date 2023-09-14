package youtube.presentation.video.video_watch_later;

import org.springframework.web.bind.annotation.*;
import youtube.application.video.video_watch_later.command.CommandVideoWatchLaterDelete;
import youtube.application.video.video_watch_later.command.CommandVideoWatchLaterSave;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;

@RequestMapping("/api")
@RestController
public class VideoWatchLaterController {

    private final CommandVideoWatchLaterSave commandVideoWatchLaterSave;
    private final CommandVideoWatchLaterDelete commandVideoWatchLaterDelete;

    public VideoWatchLaterController(final CommandVideoWatchLaterSave commandVideoWatchLaterSave,
                                     final CommandVideoWatchLaterDelete commandVideoWatchLaterDelete) {
        this.commandVideoWatchLaterSave = commandVideoWatchLaterSave;
        this.commandVideoWatchLaterDelete = commandVideoWatchLaterDelete;
    }

    @PostMapping("/videoWatchLater/{videoInfoId}")
    public void saveVideoWatchLater(@Login final MemberSession memberSession,
                                    @PathVariable final long videoInfoId) {
        commandVideoWatchLaterSave.command(memberSession.id(), videoInfoId);
    }

    @DeleteMapping("/videoWatchLater/{videoInfoId}")
    public void deleteVideoWatchLater(@Login final MemberSession memberSession,
                                      @PathVariable final long videoInfoId) {
        commandVideoWatchLaterDelete.command(memberSession.id(), videoInfoId);
    }
}
