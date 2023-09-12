package youtube.application.video_watch_later.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.video_watch_later.VideoWatchLater;
import youtube.mapper.video_watch_later.VideoWatchLaterMapper;
import youtube.mapper.video_watch_later.dto.VideoWatchLaterSaveRequest;
import youtube.repository.video_watch_later.VideoWatchLaterRepository;

@Service
public class CommandVideoWatchLaterSave {

    private final VideoWatchLaterRepository videoWatchLaterRepository;

    public CommandVideoWatchLaterSave(final VideoWatchLaterRepository videoWatchLaterRepository) {
        this.videoWatchLaterRepository = videoWatchLaterRepository;
    }

    @Transactional
    public void command(final VideoWatchLaterSaveRequest dto) {
        VideoWatchLater entity = VideoWatchLaterMapper.toEntity(dto);
        videoWatchLaterRepository.save(entity);
    }
}
