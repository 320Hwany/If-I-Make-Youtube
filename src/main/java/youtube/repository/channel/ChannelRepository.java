package youtube.repository.channel;

import youtube.domain.channel.persist.Channel;

public interface ChannelRepository {

    void save(final Channel channel);

    long count();
}
