package youtube.repository.subscription;

import youtube.domain.subscription.Subscription;
import youtube.mapper.subscription.dto.SubscriptionChannelDto;

import java.util.List;

public interface SubscriptionRepository {

    void save(final Subscription subscription);

    Subscription getByMemberIdAndChannelId(final long memberId, final long channelId);

    List<SubscriptionChannelDto> findSubscriptionChannelsByMemberId(final long memberId);

    boolean existsByMemberIdAndChannelId(final long memberId, final long channelId);

    void delete(final Subscription subscription);

    long count();
}
