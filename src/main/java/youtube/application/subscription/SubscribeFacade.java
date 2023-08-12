package youtube.application.subscription;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import youtube.repository.channel.ChannelRepository;

import static youtube.global.constant.CacheConstant.SUBSCRIBERS_COUNT;
import static youtube.global.constant.NumberConstant.ONE;

@Service
public class SubscribeFacade {

    private final ChannelRepository channelRepository;
    private final CacheManager cacheManager;

    public SubscribeFacade(final ChannelRepository channelRepository, final CacheManager cacheManager) {
        this.channelRepository = channelRepository;
        this.cacheManager = cacheManager;
    }

    @Cacheable(value = SUBSCRIBERS_COUNT, key = "#channelId")
    public int getCache(final long channelId) {
        return channelRepository.getSubscribersCountByChannelId(channelId);
    }

    public synchronized void increaseSubscribers(final long channelId) {
        Cache cache = cacheManager.getCache(SUBSCRIBERS_COUNT);
        assert cache != null;

        Cache.ValueWrapper valueWrapper = cache.get(channelId);
        assert valueWrapper != null;

        Integer currentCount = (Integer) valueWrapper.get();
        assert currentCount != null;
        cache.put(channelId, currentCount + ONE.value);
    }
}
