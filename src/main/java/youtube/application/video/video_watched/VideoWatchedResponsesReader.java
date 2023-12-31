package youtube.application.video.video_watched;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.mapper.video.video_watched.dto.VideoWatchedResponse;
import youtube.repository.video.video_watched.VideoWatchedRepository;

import java.util.List;

@Service
public class VideoWatchedResponsesReader {

    private final VideoWatchedRepository videoWatchedRepository;

    public VideoWatchedResponsesReader(final VideoWatchedRepository videoWatchedRepository) {
        this.videoWatchedRepository = videoWatchedRepository;
    }

    @Transactional(readOnly = true)
    public List<VideoWatchedResponse> findAllByMemberId(final long memberId) {
        return videoWatchedRepository.findVideoWatchedResponsesByMemberId(memberId);
    }
}
