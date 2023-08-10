package youtube.repository.subscription;

import youtube.domain.subscription.Subscription;

public interface SubscriptionRepository {

    void save(final Subscription subscription);

    Subscription getById(final long subscriptionId);

    long count();
}
