package youtube.application.subscription;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.subscription.Subscription;
import youtube.mapper.subscription.dto.SubscriptionCancelEvent;
import youtube.repository.subscription.SubscriptionRepository;

@Service
public class SubscriptionsDeleter {

    private final SubscriptionRepository subscriptionRepository;
    private final ApplicationEventPublisher publisher;

    public SubscriptionsDeleter(final SubscriptionRepository subscriptionRepository,
                                final ApplicationEventPublisher publisher) {
        this.subscriptionRepository = subscriptionRepository;
        this.publisher = publisher;
    }

    @Transactional
    public void command(final long memberId, final long channelId) {
        Subscription entity = subscriptionRepository.getByMemberIdAndChannelId(memberId, channelId);
        subscriptionRepository.delete(entity);

        // 스프링 이벤트 호출 - SubscriptionsEventListener
        publisher.publishEvent(new SubscriptionCancelEvent(memberId, channelId));
    }
}
