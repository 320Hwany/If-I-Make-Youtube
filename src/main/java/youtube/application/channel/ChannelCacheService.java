package youtube.application.channel;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import youtube.mapper.channel.dto.ChannelCache;
import youtube.repository.channel.ChannelRepository;

import static youtube.global.constant.CacheConstant.CHANNEL_CACHE;

@Service
public class ChannelCacheService {

    private final ChannelRepository channelRepository;
    private final CacheManager cacheManager;

    public ChannelCacheService(final ChannelRepository channelRepository, final CacheManager cacheManager) {
        this.channelRepository = channelRepository;
        this.cacheManager = cacheManager;
    }

    @Cacheable(value = CHANNEL_CACHE, key = "#channelId")
    public ChannelCache getCache(final long channelId) {
        return channelRepository.getChannelCacheById(channelId);
    }
}
