package youtube.repository.channel;

import org.springframework.stereotype.Repository;
import youtube.domain.channel.persist.Channel;

@Repository
public class ChannelRepositoryImpl implements ChannelRepository {

    private final ChannelJpaRepository channelJpaRepository;

    public ChannelRepositoryImpl(final ChannelJpaRepository channelJpaRepository) {
        this.channelJpaRepository = channelJpaRepository;
    }

    @Override
    public void save(final Channel channel) {
        channelJpaRepository.save(channel);
    }

    @Override
    public long count() {
        return channelJpaRepository.count();
    }
}
