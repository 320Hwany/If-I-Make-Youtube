package youtube.repository.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.subscription.Subscription;

import java.util.Optional;

public interface SubscriptionJpaRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByMemberIdAndChannelId(final long memberId, final long channelId);

    boolean existsByMemberIdAndChannelId(final long memberId, final long channelId);
}
