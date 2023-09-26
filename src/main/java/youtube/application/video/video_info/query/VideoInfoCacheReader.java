package youtube.application.video.video_info.query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.video.video_info.vo.VideoInfoCache;
import youtube.mapper.video.video_info.VideoInfoMapper;
import youtube.mapper.video.video_info.dto.VideoInfoCacheDto;
import youtube.repository.video.video_info.VideoInfoRepository;

import static youtube.global.constant.AnnotationMessageConstant.VIDEO_INFO_CACHE;

@Service
public class VideoInfoCacheReader {

    private final VideoInfoRepository videoInfoRepository;

    public VideoInfoCacheReader(final VideoInfoRepository videoInfoRepository) {
        this.videoInfoRepository = videoInfoRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = VIDEO_INFO_CACHE, key = "#videoInfoId")
    public VideoInfoCache query(final long videoInfoId) {
        VideoInfoCacheDto dto = videoInfoRepository.getVideoInfoCacheDtoById(videoInfoId);
        return VideoInfoMapper.toCache(dto);
    }
}
