package youtube.application.video.video_watched.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.mapper.video.video_watched.dto.VideoWatchedResponse;
import youtube.repository.video.video_watched.VideoWatchedRepository;

import java.util.List;

@Service
public class QueryVideoWatchedCacheByMemberId {

    private final VideoWatchedRepository videoWatchedRepository;

    public QueryVideoWatchedCacheByMemberId(final VideoWatchedRepository videoWatchedRepository) {
        this.videoWatchedRepository = videoWatchedRepository;
    }

    @Transactional(readOnly = true)
    public List<VideoWatchedResponse> query(final long memberId) {
        return videoWatchedRepository.findVideoWatchedResponsesByMemberId(memberId);
    }
}
