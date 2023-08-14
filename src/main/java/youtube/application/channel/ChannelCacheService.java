package youtube.application.channel;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import youtube.domain.channel.vo.ChannelCache;
import youtube.mapper.channel.ChannelMapper;
import youtube.mapper.channel.dto.ChannelCacheDto;
import youtube.repository.channel.ChannelRepository;

import static youtube.global.constant.CacheConstant.CHANNEL_CACHE;

@Service
public class ChannelCacheService {

    private final ChannelRepository channelRepository;

    public ChannelCacheService(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Cacheable(value = CHANNEL_CACHE, key = "#channelId")
    public ChannelCache getCache(final long channelId) {
        ChannelCacheDto dto = channelRepository.getChannelCacheDtoById(channelId);
        return ChannelMapper.toChannelCache(dto);
    }
}
