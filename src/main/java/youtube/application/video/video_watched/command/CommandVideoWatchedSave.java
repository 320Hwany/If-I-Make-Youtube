package youtube.application.video.video_watched.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.video.video_watched.persist.VideoWatched;
import youtube.mapper.video.video_watched.VideoWatchedMapper;
import youtube.repository.video.video_watched.VideoWatchedRepository;

import java.time.LocalDateTime;

@Service
public class CommandVideoWatchedSave {

    private final VideoWatchedRepository videoWatchedRepository;

    public CommandVideoWatchedSave(final VideoWatchedRepository videoWatchedRepository) {
        this.videoWatchedRepository = videoWatchedRepository;
    }

    @Transactional
    public void command(final long memberId, final long videoInfoId, final LocalDateTime watchedDateTime) {
        VideoWatched entity = VideoWatchedMapper.toEntity(memberId, videoInfoId, watchedDateTime);
        videoWatchedRepository.save(entity);
    }
}