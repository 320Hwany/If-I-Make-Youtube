package youtube.presentation.video.video_watch_later;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youtube.application.video.video_watch_later.command.CommandVideoWatchLaterSave;
import youtube.mapper.video.video_watch_later.dto.VideoWatchLaterSaveRequest;

@RequestMapping("/api")
@RestController
public class VideoWatchLaterController {

    private final CommandVideoWatchLaterSave commandVideoWatchLaterSave;

    public VideoWatchLaterController(final CommandVideoWatchLaterSave commandVideoWatchLaterSave) {
        this.commandVideoWatchLaterSave = commandVideoWatchLaterSave;
    }

    @PostMapping("/videoWatchLater")
    public void saveVideoWatchLater(@RequestBody final VideoWatchLaterSaveRequest dto) {
        commandVideoWatchLaterSave.command(dto);
    }
}
