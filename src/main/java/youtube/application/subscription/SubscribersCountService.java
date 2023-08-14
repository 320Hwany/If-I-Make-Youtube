package youtube.application.subscription;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import youtube.domain.channel.vo.ChannelCache;

import static youtube.global.constant.CacheConstant.CHANNEL_CACHE;

@Service
public class SubscribersCountService {

    private final CacheManager cacheManager;

    public SubscribersCountService(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public synchronized void increaseCount(final long channelId, final ChannelCache channelCache) {
        Cache cache = cacheManager.getCache(CHANNEL_CACHE);
        assert cache != null;
        channelCache.increaseSubscribersCount();
        cache.put(channelId, channelCache);
    }
}
