package youtube.repository.channel;

import youtube.domain.channel.persist.Channel;

public interface ChannelRepository {

    void save(final Channel channel);

    Channel getById(final long channelId);

    Channel getByMemberId(final long memberId);

    int getSubscribersCountByChannelId(final long channelId);

    long count();
}
