package youtube.application.video_reaction;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import youtube.application.video_info.query.QueryVideoInfoCacheById;
import youtube.domain.video_info.vo.VideoInfoCache;
import youtube.domain.video_reaction.vo.Reaction;

import static youtube.global.constant.AnnotationMessageConstant.VIDEO_INFO_CACHE;

@Service
public class VideoReactionUpdateService {

    private final QueryVideoInfoCacheById queryVideoInfoCacheById;
    private final CacheManager cacheManager;

    public VideoReactionUpdateService(final QueryVideoInfoCacheById queryVideoInfoCacheById,
                                      final CacheManager cacheManager) {
        this.queryVideoInfoCacheById = queryVideoInfoCacheById;
        this.cacheManager = cacheManager;
    }

    public synchronized void updateReactionCount(final long videoInfoId, final Reaction updateReaction,
                                                  final Reaction originalReaction) {
        VideoInfoCache videoInfoCache = queryVideoInfoCacheById.query(videoInfoId);
        videoInfoCache.updateReactionCount(originalReaction, updateReaction);
        Cache cache = cacheManager.getCache(VIDEO_INFO_CACHE);
        assert cache != null;
        cache.put(videoInfoId, videoInfoCache);
    }
}
