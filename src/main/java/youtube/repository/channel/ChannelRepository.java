package youtube.repository.channel;

import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.ChannelCache;
import youtube.mapper.channel.dto.ChannelCacheDto;

public interface ChannelRepository {

    void save(final Channel channel);

    Channel getById(final long channelId);

    Channel getByMemberId(final long memberId);

    ChannelCacheDto getChannelCacheDtoById(final long channelId);

    long count();
}
