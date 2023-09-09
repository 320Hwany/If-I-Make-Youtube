package youtube.application.video_info.query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.video_info.vo.VideoInfoCache;
import youtube.mapper.video_info.VideoInfoMapper;
import youtube.mapper.video_info.dto.VideoInfoCacheDto;
import youtube.repository.video_info.VideoInfoRepository;

import static youtube.global.constant.AnnotationMessageConstant.VIDEO_INFO_CACHE;

@Service
public class QueryVideoInfoCacheById {

    private final VideoInfoRepository videoInfoRepository;

    public QueryVideoInfoCacheById(final VideoInfoRepository videoInfoRepository) {
        this.videoInfoRepository = videoInfoRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = VIDEO_INFO_CACHE, key = "#videoInfoId")
    public VideoInfoCache query(final long videoInfoId) {
        VideoInfoCacheDto dto = videoInfoRepository.getVideoInfoCacheDtoById(videoInfoId);
        return VideoInfoMapper.toCache(dto);
    }
}
