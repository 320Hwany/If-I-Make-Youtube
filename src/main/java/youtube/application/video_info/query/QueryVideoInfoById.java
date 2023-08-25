package youtube.application.video_info.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.video_info.persist.VideoInfo;
import youtube.repository.video_info.VideoInfoRepository;

@Service
public class QueryVideoInfoById {

    private final VideoInfoRepository videoInfoRepository;

    public QueryVideoInfoById(final VideoInfoRepository videoInfoRepository) {
        this.videoInfoRepository = videoInfoRepository;
    }

    @Transactional(readOnly = true)
    public VideoInfo query(final long videoInfoId) {
        return videoInfoRepository.getById(videoInfoId);
    }
}
