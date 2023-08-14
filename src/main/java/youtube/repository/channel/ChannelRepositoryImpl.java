package youtube.repository.channel;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.QChannelCache;
import youtube.global.exception.NotFoundException;
import youtube.domain.channel.vo.ChannelCache;

import static youtube.domain.channel.persist.QChannel.*;
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
    public ChannelCache getChannelCacheById(final long channelId) {
        return queryFactory.select(new QChannelCache(
                        channel.channelName,
                        channel.channelDescription,
                        channel.videosCount,
                        channel.subscribersCount
                ))
                .from(channel)
                .where(channel.id.eq(channelId))
                .fetchFirst();
    }

    @Override
    public int getSubscribersCountByChannelId(final long channelId) {
        return queryFactory.select(channel.subscribersCount)
                .from(channel)
                .where(channel.id.eq(channelId))
                .fetchFirst();
    }

    @Override
    public long count() {
        return channelJpaRepository.count();
    }
}
