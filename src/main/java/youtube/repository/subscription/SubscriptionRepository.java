package youtube.repository.subscription;

import youtube.domain.subscription.Subscription;

public interface SubscriptionRepository {

    void save(final Subscription subscription);

    Subscription getByMemberIdAndChannelId(final long memberId, final long channelId);

    boolean existsByMemberIdAndChannelId(final long memberId, final long channelId);

    void delete(final Subscription subscription);

    long count();
}
