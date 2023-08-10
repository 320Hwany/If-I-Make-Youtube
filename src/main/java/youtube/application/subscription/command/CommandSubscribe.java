package youtube.application.subscription.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.subscription.Subscription;
import youtube.mapper.subscription.SubscriptionMapper;
import youtube.repository.subscription.SubscriptionRepository;

@Transactional
@Service
public class CommandSubscribe {

    private final SubscriptionRepository subscriptionRepository;

    public CommandSubscribe(final SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public void command(final long memberId, final long channelId) {
        Subscription entity = SubscriptionMapper.toEntity(memberId, channelId);
        subscriptionRepository.save(entity);
    }
}
