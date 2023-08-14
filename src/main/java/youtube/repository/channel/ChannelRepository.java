package youtube.repository.channel;

import youtube.domain.channel.persist.Channel;
import youtube.mapper.channel.dto.ChannelCache;

public interface ChannelRepository {

    void save(final Channel channel);

    Channel getById(final long channelId);

    Channel getByMemberId(final long memberId);

    ChannelCache getChannelCacheById(final long channelId);

    int getSubscribersCountByChannelId(final long channelId);

    long count();
}
