package youtube.application.channel.implement;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.vo.ChannelCache;
import youtube.mapper.channel.ChannelMapper;
import youtube.mapper.channel.dto.ChannelCacheDto;
import youtube.repository.channel.ChannelRepository;

import static youtube.global.constant.AnnotationMessageConstant.CHANNEL_CACHE;

@Service
public class ChannelCacheReader {

    private final ChannelRepository channelRepository;

    public ChannelCacheReader(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = CHANNEL_CACHE, key = "#channelId")
    public ChannelCache getByChannelId(final long channelId) {
        ChannelCacheDto dto = channelRepository.getChannelCacheDtoById(channelId);
        return ChannelMapper.toChannelCache(dto);
    }
}
