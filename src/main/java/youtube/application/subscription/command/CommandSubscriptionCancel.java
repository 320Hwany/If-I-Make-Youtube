package youtube.application.subscription.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.subscription.Subscription;
import youtube.repository.subscription.SubscriptionRepository;

@Service
public class CommandSubscriptionCancel {

    private final SubscriptionRepository subscriptionRepository;

    public CommandSubscriptionCancel(final SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional
    public void command(final long memberId, final long channelId) {
        Subscription entity = subscriptionRepository.getByMemberIdAndChannelId(memberId, channelId);
        subscriptionRepository.delete(entity);
    }
}
