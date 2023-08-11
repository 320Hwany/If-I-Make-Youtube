package youtube.repository.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.subscription.Subscription;

public interface SubscriptionJpaRepository extends JpaRepository<Subscription, Long> {

    boolean existsByMemberIdOrChannelId(final long memberId, final long channelId);
}
