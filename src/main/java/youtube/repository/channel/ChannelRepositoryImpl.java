package youtube.repository.channel;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.persist.QChannel;
import youtube.domain.channel.vo.ChannelCache;
import youtube.global.exception.NotFoundException;
import youtube.mapper.channel.dto.ChannelCacheDto;
import youtube.mapper.channel.dto.QChannelCacheDto;

import java.util.List;

import static youtube.domain.channel.persist.QChannel.channel;
import static youtube.global.constant.ExceptionMessageConstant.*;

@Repository
public class ChannelRepositoryImpl implements ChannelRepository {

    private final ChannelJpaRepository channelJpaRepository;
    private final JPAQueryFactory queryFactory;

    public ChannelRepositoryImpl(final ChannelJpaRepository channelJpaRepository,
                                 final JPAQueryFactory queryFactory) {
        this.channelJpaRepository = channelJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public void save(final Channel channel) {
        channelJpaRepository.save(channel);
    }

    @Override
    public Channel getById(final long channelId) {
        return channelJpaRepository.findById(channelId)
                .orElseThrow(() -> new NotFoundException(CHANNEL_NOT_FOUND.message));
    }

    @Override
    public Channel getByMemberId(final long memberId) {
        return channelJpaRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND.message));
    }

    @Override
    public ChannelCacheDto getChannelCacheDtoById(final long channelId) {
        ChannelCacheDto channelCacheDto =
                queryFactory.select(
                                new QChannelCacheDto(
                                        channel.channelName,
                                        channel.channelDescription,
                                        channel.videosCount,
                                        channel.subscribersCount
                                ))
                        .from(channel)
                        .where(channel.id.eq(channelId))
                        .fetchFirst();

        if (channelCacheDto != null) {
            return channelCacheDto;
        }

        throw new NotFoundException(CHANNEL_NOT_FOUND.message);
    }

    @Override
    public List<Channel> findAll() {
        return channelJpaRepository.findAll();
    }

    @Override
    public long count() {
        return channelJpaRepository.count();
    }
}
