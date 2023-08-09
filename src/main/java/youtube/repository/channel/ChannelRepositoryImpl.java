package youtube.repository.channel;

import org.springframework.stereotype.Repository;
import youtube.domain.channel.persist.Channel;
import youtube.global.constant.ExceptionMessageConstant;
import youtube.global.exception.BadRequestException;
import youtube.global.exception.NotFoundException;

import static youtube.global.constant.ExceptionMessageConstant.*;

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
    public Channel getByMemberId(final long memberId) {
        return channelJpaRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND.message));
    }

    @Override
    public long count() {
        return channelJpaRepository.count();
    }
}
