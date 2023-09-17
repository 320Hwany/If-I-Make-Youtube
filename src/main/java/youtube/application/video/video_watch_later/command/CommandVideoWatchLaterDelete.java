package youtube.application.video.video_watch_later.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.video.video_watch_later.persist.VideoWatchLater;
import youtube.repository.video.video_watch_later.VideoWatchLaterRepository;

@Service
public class CommandVideoWatchLaterDelete {

    private final VideoWatchLaterRepository videoWatchLaterRepository;

    public CommandVideoWatchLaterDelete(final VideoWatchLaterRepository videoWatchLaterRepository) {
        this.videoWatchLaterRepository = videoWatchLaterRepository;
    }

    @Transactional
    public void command(final long memberId, final long videoInfoId) {
        VideoWatchLater entity = videoWatchLaterRepository.getByMemberIdAndVideoInfoId(memberId, videoInfoId);
        videoWatchLaterRepository.delete(entity);
    }
}
