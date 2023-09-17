package youtube.application.video.video_watch_later.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.video.video_watch_later.persist.VideoWatchLater;
import youtube.mapper.video.video_watch_later.VideoWatchLaterMapper;
import youtube.repository.video.video_watch_later.VideoWatchLaterRepository;

@Service
public class CommandVideoWatchLaterSave {

    private final VideoWatchLaterRepository videoWatchLaterRepository;

    public CommandVideoWatchLaterSave(final VideoWatchLaterRepository videoWatchLaterRepository) {
        this.videoWatchLaterRepository = videoWatchLaterRepository;
    }

    @Transactional
    public void command(final long memberId, final long videoInfoId) {
        if (!videoWatchLaterRepository.existsByMemberIdAndVideoInfoId(memberId, videoInfoId)) {
            VideoWatchLater entity = VideoWatchLaterMapper.toEntity(memberId, videoInfoId);
            videoWatchLaterRepository.save(entity);
        }
    }
}
