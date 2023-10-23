package youtube.presentation.video.video_watched;

import org.springframework.web.bind.annotation.*;
import youtube.application.video.video_watched.VideoWatchedUpdater;
import youtube.application.video.video_watched.VideoWatchedResponsesReader;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;
import youtube.mapper.video.video_watched.VideoWatchedMapper;
import youtube.mapper.video.video_watched.dto.VideoWatchedResponse;
import youtube.mapper.video.video_watched.dto.VideoWatchedResult;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api")
@RestController
public class VideoWatchedController {

    private final VideoWatchedResponsesReader videoWatchedResponsesReader;
    private final VideoWatchedUpdater videoWatchedUpdater;

    public VideoWatchedController(final VideoWatchedResponsesReader videoWatchedResponsesReader,
                                  final VideoWatchedUpdater videoWatchedUpdater) {
        this.videoWatchedResponsesReader = videoWatchedResponsesReader;
        this.videoWatchedUpdater = videoWatchedUpdater;
    }

    @GetMapping("/v2/video-watched")
    public VideoWatchedResult getVideoWatched(@Login final MemberSession memberSession){
        List<VideoWatchedResponse> responses = videoWatchedResponsesReader.findAllByMemberId(memberSession.id());
        return VideoWatchedMapper.toResult(responses.size(), responses);
    }

    @PostMapping("/v2/video-watched/{videoInfoId}")
    public void updateVideoWatched(@Login final MemberSession memberSession,
                                   @PathVariable final long videoInfoId) {
        videoWatchedUpdater.command(memberSession.id(), videoInfoId, LocalDateTime.now());
    }
}
