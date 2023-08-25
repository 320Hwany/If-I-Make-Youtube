package youtube.repository.video_info;

import org.springframework.stereotype.Repository;
import youtube.domain.video_info.persist.VideoInfo;
import youtube.global.exception.NotFoundException;

import static youtube.global.constant.ExceptionMessageConstant.*;

@Repository
public class VideoInfoRepositoryImpl implements VideoInfoRepository {

    private final VideoInfoJpaRepository videoInfoJpaRepository;

    public VideoInfoRepositoryImpl(final VideoInfoJpaRepository videoInfoJpaRepository) {
        this.videoInfoJpaRepository = videoInfoJpaRepository;
    }

    @Override
    public void save(final VideoInfo videoInfo) {
        videoInfoJpaRepository.save(videoInfo);
    }

    @Override
    public VideoInfo getById(final long videoId) {
        return videoInfoJpaRepository.findById(videoId)
                .orElseThrow(() -> new NotFoundException(VIDEO_INFO_NOT_FOUND.message));
    }

    @Override
    public long count() {
        return videoInfoJpaRepository.count();
    }
}
