package youtube.repository.channel;

import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.ChannelCache;
import youtube.mapper.channel.dto.ChannelCacheDto;

import java.util.List;

public interface ChannelRepository {

    void save(final Channel channel);

    Channel getById(final long channelId);

    Channel getByMemberId(final long memberId);

    ChannelCacheDto getChannelCacheDtoById(final long channelId);

    List<Channel> findAll();

    long count();
}
