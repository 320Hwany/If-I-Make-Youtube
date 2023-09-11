package youtube.application.video_info;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import youtube.application.video_info.command.CommandVideoInfoCacheUpdate;

@Service
public class VideoInfoCacheSyncService {

    private final CommandVideoInfoCacheUpdate commandVideoInfoCacheUpdate;
    private final CacheManager cacheManager;

    public VideoInfoCacheSyncService(final CommandVideoInfoCacheUpdate commandVideoInfoCacheUpdate,
                                     final CacheManager cacheManager) {
        this.commandVideoInfoCacheUpdate = commandVideoInfoCacheUpdate;
        this.cacheManager = cacheManager;
    }
}
