package youtube.application.video_reaction;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import youtube.application.video_info.query.QueryVideoInfoCacheById;
import youtube.domain.video_info.vo.VideoInfoCache;
import youtube.mapper.video_reaction.dto.VideoReactionRequest;

import static youtube.global.constant.AnnotationMessageConstant.VIDEO_INFO_CACHE;

@Service
public class VideoReactionCacheUpdateService {

    private final QueryVideoInfoCacheById queryVideoInfoCacheById;
    private final CacheManager cacheManager;

    public VideoReactionCacheUpdateService(final QueryVideoInfoCacheById queryVideoInfoCacheById,
                                           final CacheManager cacheManager) {
        this.queryVideoInfoCacheById = queryVideoInfoCacheById;
        this.cacheManager = cacheManager;
    }

    public synchronized void updateCache(final VideoReactionRequest dto) {
        VideoInfoCache videoInfoCache = queryVideoInfoCacheById.query(dto.videoInfoId());
        videoInfoCache.updateReactionCount(dto.originalReaction(), dto.updateReaction());
        Cache cache = cacheManager.getCache(VIDEO_INFO_CACHE);
        assert cache != null;
        cache.put(dto.videoInfoId(), videoInfoCache);
    }
}
