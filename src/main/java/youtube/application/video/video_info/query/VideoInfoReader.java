package youtube.application.video.video_info.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.repository.video.video_info.VideoInfoRepository;

@Service
public class VideoInfoReader {

    private final VideoInfoRepository videoInfoRepository;

    public VideoInfoReader(final VideoInfoRepository videoInfoRepository) {
        this.videoInfoRepository = videoInfoRepository;
    }

    @Transactional(readOnly = true)
    public VideoInfo query(final long videoInfoId) {
        return videoInfoRepository.getById(videoInfoId);
    }
}
