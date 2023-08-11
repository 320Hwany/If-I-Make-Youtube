package youtube.application.subscription.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.application.subscription.SubscribersFacade;
import youtube.domain.subscription.Subscription;
import youtube.global.exception.BadRequestException;
import youtube.mapper.subscription.SubscriptionMapper;
import youtube.repository.subscription.SubscriptionRepository;

import static youtube.global.constant.ExceptionMessageConstant.*;


@Service
public class CommandSubscribe {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscribersFacade subscribersFacade;

    public CommandSubscribe(final SubscriptionRepository subscriptionRepository,
                            final SubscribersFacade subscribersFacade) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscribersFacade = subscribersFacade;
    }

    @Transactional
    public void command(final long memberId, final long channelId) {
        if (validateDuplication(memberId, channelId)) {
            throw new BadRequestException(SUBSCRIBE_DUPLICATION.message);
        }
        Subscription subscription = SubscriptionMapper.toEntity(memberId, channelId);
        subscriptionRepository.save(subscription);
        subscribersFacade.getCache(channelId);
        subscribersFacade.increaseSubscribers(channelId);
    }

    private boolean validateDuplication(final long memberId, final long channelId) {
        return subscriptionRepository.existsByMemberIdOrChannelId(memberId, channelId);
    }
}
