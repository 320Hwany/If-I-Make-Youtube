package youtube.repository.subscription;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import youtube.domain.subscription.Subscription;
import youtube.global.exception.NotFoundException;
import youtube.mapper.subscription.dto.QSubscriptionChannelDto;
import youtube.mapper.subscription.dto.SubscriptionChannelDto;

import java.util.List;

import static youtube.domain.channel.persist.QChannel.channel;
import static youtube.domain.subscription.QSubscription.subscription;
import static youtube.global.constant.ExceptionMessageConstant.*;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final SubscriptionJpaRepository subscriptionJpaRepository;
    private final JPAQueryFactory queryFactory;

    public SubscriptionRepositoryImpl(final SubscriptionJpaRepository subscriptionJpaRepository,
                                      final JPAQueryFactory queryFactory) {
        this.subscriptionJpaRepository = subscriptionJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public void save(final Subscription subscription) {
        subscriptionJpaRepository.save(subscription);
    }

    @Override
    public Subscription getByMemberIdAndChannelId(final long memberId, final long channelId) {
        return subscriptionJpaRepository.findByMemberIdAndChannelId(memberId, channelId)
                .orElseThrow(() -> new NotFoundException(SUBSCRIPTION_NOT_FOUND.message));
    }

    @Override
    public List<SubscriptionChannelDto> findSubscriptionChannelsByMemberId(final long memberId) {
        return queryFactory.select(
                        new QSubscriptionChannelDto(
                                channel.id,
                                channel.channelName
                        ))
                .from(subscription)
                .innerJoin(channel).on(subscription.channelId.eq(channel.id))
                .where(subscription.memberId.eq(memberId))
                .orderBy(subscription.id.desc())
                .fetch();
    }

    @Override
    public boolean existsByMemberIdAndChannelId(final long memberId, final long channelId) {
        return subscriptionJpaRepository.existsByMemberIdAndChannelId(memberId, channelId);
    }

    @Override
    public void delete(final Subscription subscription) {
        subscriptionJpaRepository.delete(subscription);
    }

    @Override
    public long count() {
        return subscriptionJpaRepository.count();
    }
}
