package youtube.repository.subscription;

import org.springframework.stereotype.Repository;
import youtube.domain.subscription.Subscription;
import youtube.global.exception.NotFoundException;

import static youtube.global.constant.ExceptionMessageConstant.*;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final SubscriptionJpaRepository subscriptionJpaRepository;

    public SubscriptionRepositoryImpl(final SubscriptionJpaRepository subscriptionJpaRepository) {
        this.subscriptionJpaRepository = subscriptionJpaRepository;
    }

    @Override
    public void save(final Subscription subscription) {
        subscriptionJpaRepository.save(subscription);
    }

    @Override
    public Subscription getById(final long subscriptionId) {
        return subscriptionJpaRepository.findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException(SUBSCRIPTION_NOT_FOUND.message));
    }

    @Override
    public boolean existsByMemberIdAndChannelId(final long memberId, final long channelId) {
        return subscriptionJpaRepository.existsByMemberIdAndChannelId(memberId, channelId);
    }

    @Override
    public long count() {
        return subscriptionJpaRepository.count();
    }
}
