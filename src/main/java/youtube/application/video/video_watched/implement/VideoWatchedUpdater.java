package youtube.application.video.video_watched.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.video.video_watched.persist.VideoWatched;
import youtube.mapper.video.video_watched.VideoWatchedMapper;
import youtube.repository.video.video_watched.VideoWatchedRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VideoWatchedUpdater {

    private final VideoWatchedRepository videoWatchedRepository;

    public VideoWatchedUpdater(final VideoWatchedRepository videoWatchedRepository) {
        this.videoWatchedRepository = videoWatchedRepository;
    }

    @Transactional
    public void command(final long memberId, final long videoInfoId, final LocalDateTime watchedDateTime) {
        Optional<VideoWatched> optionalVideoWatched =
                videoWatchedRepository.findByMemberIdAndVideoInfoId(memberId, videoInfoId);

        if (optionalVideoWatched.isPresent()) {
            VideoWatched entity = optionalVideoWatched.get();
            entity.updateLastWatchedDateTime(watchedDateTime);
        } else {
            VideoWatched entity = VideoWatchedMapper.toEntity(memberId, videoInfoId, watchedDateTime);
            videoWatchedRepository.save(entity);
        }
    }
}
