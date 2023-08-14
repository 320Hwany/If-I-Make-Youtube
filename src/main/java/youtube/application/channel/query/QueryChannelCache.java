package youtube.application.channel.query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.vo.ChannelCache;
import youtube.mapper.channel.ChannelMapper;
import youtube.mapper.channel.dto.ChannelCacheDto;
import youtube.repository.channel.ChannelRepository;

import static youtube.global.constant.CacheConstant.CHANNEL_CACHE;

@Transactional(readOnly = true)
@Service
public class QueryChannelCache {

    private final ChannelRepository channelRepository;

    public QueryChannelCache(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Cacheable(value = CHANNEL_CACHE, key = "#channelId")
    public ChannelCache getCache(final long channelId) {
        ChannelCacheDto dto = channelRepository.getChannelCacheDtoById(channelId);
        return ChannelMapper.toChannelCache(dto);
    }
}
