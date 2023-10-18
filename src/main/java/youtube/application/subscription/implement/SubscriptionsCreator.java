package youtube.application.subscription.implement;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.subscription.Subscription;
import youtube.global.exception.BadRequestException;
import youtube.mapper.subscription.SubscriptionMapper;
import youtube.mapper.subscription.dto.SubscriptionSaveEvent;
import youtube.repository.subscription.SubscriptionRepository;

import static youtube.global.constant.ExceptionMessageConstant.*;


@Service
public class SubscriptionsCreator {

    private final SubscriptionRepository subscriptionRepository;
    private final ApplicationEventPublisher publisher;

    public SubscriptionsCreator(final SubscriptionRepository subscriptionRepository,
                                final ApplicationEventPublisher publisher) {
        this.subscriptionRepository = subscriptionRepository;
        this.publisher = publisher;
    }

    @Transactional
    public void command(final long memberId, final long channelId) {
        if (validateDuplication(memberId, channelId)) {
            throw new BadRequestException(SUBSCRIBE_DUPLICATION.message);
        }

        Subscription subscription = SubscriptionMapper.toEntity(memberId, channelId);
        subscriptionRepository.save(subscription);

        // 스프링 이벤트 호출 - SubscriptionsEventListener
        publisher.publishEvent(new SubscriptionSaveEvent(memberId, channelId));
    }

    private boolean validateDuplication(final long memberId, final long channelId) {
        return subscriptionRepository.existsByMemberIdAndChannelId(memberId, channelId);
    }
}
