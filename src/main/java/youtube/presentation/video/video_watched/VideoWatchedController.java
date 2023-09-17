package youtube.presentation.video.video_watched;

import org.springframework.web.bind.annotation.*;
import youtube.application.video.video_watched.command.CommandVideoWatchedUpdate;
import youtube.application.video.video_watched.query.QueryVideoWatchedCacheByMemberId;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;
import youtube.mapper.video.video_watched.dto.VideoWatchedResponse;
import youtube.mapper.video.video_watched.dto.VideoWatchedResult;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api")
@RestController
public class VideoWatchedController {

    private final QueryVideoWatchedCacheByMemberId queryVideoWatchedCacheByMemberId;
    private final CommandVideoWatchedUpdate commandVideoWatchedUpdate;

    public VideoWatchedController(final QueryVideoWatchedCacheByMemberId queryVideoWatchedCacheByMemberId,
                                  final CommandVideoWatchedUpdate commandVideoWatchedUpdate) {
        this.queryVideoWatchedCacheByMemberId = queryVideoWatchedCacheByMemberId;
        this.commandVideoWatchedUpdate = commandVideoWatchedUpdate;
    }

    @GetMapping("/video-watched")
    public VideoWatchedResult getVideoWatched(@Login final MemberSession memberSession){
        List<VideoWatchedResponse> responses = queryVideoWatchedCacheByMemberId.query(memberSession.id());
        return VideoWatchedResult.from(responses.size(), responses);
    }

    @PostMapping("/video-watched/{videoInfoId}")
    public void updateVideoWatched(@Login final MemberSession memberSession,
                                   @PathVariable final long videoInfoId) {
        commandVideoWatchedUpdate.command(memberSession.id(), videoInfoId, LocalDateTime.now());
    }
}
