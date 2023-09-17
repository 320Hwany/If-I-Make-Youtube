package youtube.application.video.video_watched.query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.video.video_watched.vo.VideoWatchedCache;
import youtube.mapper.video.video_watched.VideoWatchedMapper;
import youtube.mapper.video.video_watched.dto.VideoWatchedCacheDto;
import youtube.repository.video.video_watched.VideoWatchedRepository;

import java.util.List;

import static youtube.global.constant.AnnotationMessageConstant.VIDEO_INFO_CACHE;

@Service
public class QueryVideoWatchedCacheById {

    private final VideoWatchedRepository videoWatchedRepository;

    public QueryVideoWatchedCacheById(final VideoWatchedRepository videoWatchedRepository) {
        this.videoWatchedRepository = videoWatchedRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = VIDEO_INFO_CACHE, key = "#memberId")
    public List<VideoWatchedCache> query(final long memberId) {
        List<VideoWatchedCacheDto> dtos = videoWatchedRepository.getVideoWatchedCacheDtosByMemberId(memberId);
        return VideoWatchedMapper.toCaches(dtos);
    }
}
