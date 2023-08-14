package youtube.application.subscription;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import youtube.mapper.channel.dto.ChannelCache;

import static youtube.global.constant.CacheConstant.CHANNEL_CACHE;

@Service
public class SubscribeFacade {

    private final CacheManager cacheManager;

    public SubscribeFacade(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public synchronized void increaseSubscribers(final long channelId, final ChannelCache channelCache) {
        Cache cache = cacheManager.getCache(CHANNEL_CACHE);
        assert cache != null;
        channelCache.increaseSubscribersCount();
        cache.put(channelId, channelCache);
    }
}
