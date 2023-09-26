package youtube.presentation.video.video_watch_later;

import org.springframework.web.bind.annotation.*;
import youtube.application.video.video_watch_later.command.VideoWatchLaterDeleter;
import youtube.application.video.video_watch_later.command.VideoWatchLaterCreator;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;

@RequestMapping("/api")
@RestController
public class VideoWatchLaterController {

    private final VideoWatchLaterCreator videoWatchLaterCreator;
    private final VideoWatchLaterDeleter videoWatchLaterDeleter;

    public VideoWatchLaterController(final VideoWatchLaterCreator videoWatchLaterCreator,
                                     final VideoWatchLaterDeleter videoWatchLaterDeleter) {
        this.videoWatchLaterCreator = videoWatchLaterCreator;
        this.videoWatchLaterDeleter = videoWatchLaterDeleter;
    }

    @PostMapping("/video-watch-later/{videoInfoId}")
    public void saveVideoWatchLater(@Login final MemberSession memberSession,
                                    @PathVariable final long videoInfoId) {
        videoWatchLaterCreator.command(memberSession.id(), videoInfoId);
    }

    @DeleteMapping("/video-watch-later/{videoInfoId}")
    public void deleteVideoWatchLater(@Login final MemberSession memberSession,
                                      @PathVariable final long videoInfoId) {
        videoWatchLaterDeleter.command(memberSession.id(), videoInfoId);
    }
}
