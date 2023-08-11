package youtube.application.subscription;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.repository.channel.ChannelRepository;

import static youtube.global.constant.CacheConstant.SUBSCRIBERS_COUNT;
import static youtube.global.constant.NumberConstant.ONE;

@Service
public class SubscribersFacade {

    private final ChannelRepository channelRepository;
    private final CacheManager cacheManager;

    public SubscribersFacade(final ChannelRepository channelRepository, final CacheManager cacheManager) {
        this.channelRepository = channelRepository;
        this.cacheManager = cacheManager;
    }

    @Cacheable(value = SUBSCRIBERS_COUNT, key = "#channelId")
    public int getCache(final long channelId) {
        return channelRepository.getSubscribersCountByChannelId(channelId);
    }

    // todo 동시성 문제, DB 동기화 문제
    public void increaseSubscribers(final long channelId) {
        Cache cache = cacheManager.getCache(SUBSCRIBERS_COUNT);
        assert cache != null;
        Cache.ValueWrapper valueWrapper = cache.get(channelId);

        if (valueWrapper != null) {
            Object value = valueWrapper.get();
            if (value instanceof Integer) {
                int currentCount = (int) value;
                cache.put(channelId, currentCount + ONE.value);
            }
        }
    }
}
