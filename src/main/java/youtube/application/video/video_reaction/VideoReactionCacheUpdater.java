package youtube.application.video.video_reaction;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import youtube.application.video.video_info.query.VideoInfoCacheReader;
import youtube.domain.video.video_info.vo.VideoInfoCache;
import youtube.mapper.video.video_reaction.dto.VideoReactionRequest;

import static youtube.global.constant.AnnotationMessageConstant.VIDEO_INFO_CACHE;

@Service
public class VideoReactionCacheUpdater {

    private final VideoInfoCacheReader videoInfoCacheReader;
    private final CacheManager cacheManager;

    public VideoReactionCacheUpdater(final VideoInfoCacheReader videoInfoCacheReader,
                                     final CacheManager cacheManager) {
        this.videoInfoCacheReader = videoInfoCacheReader;
        this.cacheManager = cacheManager;
    }

    public synchronized void updateCache(final VideoReactionRequest dto) {
        VideoInfoCache videoInfoCache = videoInfoCacheReader.query(dto.videoInfoId());
        videoInfoCache.updateReactionCount(dto.originalReaction(), dto.updateReaction());
        Cache cache = cacheManager.getCache(VIDEO_INFO_CACHE);
        assert cache != null;
        cache.put(dto.videoInfoId(), videoInfoCache);
    }
}
