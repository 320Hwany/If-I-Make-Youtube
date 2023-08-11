package youtube.application.subscription.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.application.subscription.SubscribersFacade;
import youtube.domain.subscription.Subscription;
import youtube.mapper.subscription.SubscriptionMapper;
import youtube.repository.subscription.SubscriptionRepository;


@Service
public class CommandSubscribe {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscribersFacade subscribersFacade;

    public CommandSubscribe(final SubscriptionRepository subscriptionRepository,
                            final SubscribersFacade subscribersFacade) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscribersFacade = subscribersFacade;
    }

    // todo 중복 구독 방지
    @Transactional
    public void command(final long memberId, final long channelId) {
        Subscription subscription = SubscriptionMapper.toEntity(memberId, channelId);
        subscriptionRepository.save(subscription);
        subscribersFacade.getCache(channelId);
        subscribersFacade.increaseSubscribers(channelId);
    }
}
