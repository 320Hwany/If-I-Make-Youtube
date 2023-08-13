package youtube.application.subscription.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.application.subscription.SubscribeFacade;
import youtube.domain.subscription.Subscription;
import youtube.global.exception.BadRequestException;
import youtube.mapper.subscription.SubscriptionMapper;
import youtube.repository.subscription.SubscriptionRepository;

import static youtube.global.constant.ExceptionMessageConstant.*;


@Service
public class CommandSubscribe {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscribeFacade subscribeFacade;

    public CommandSubscribe(final SubscriptionRepository subscriptionRepository,
                            final SubscribeFacade subscribeFacade) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscribeFacade = subscribeFacade;
    }

    @Transactional
    public void command(final long memberId, final long channelId) {
        if (validateDuplication(memberId, channelId)) {
            throw new BadRequestException(SUBSCRIBE_DUPLICATION.message);
        }
        Subscription subscription = SubscriptionMapper.toEntity(memberId, channelId);
        subscriptionRepository.save(subscription);
        subscribeFacade.getCache(channelId);
        subscribeFacade.increaseSubscribers(channelId);
    }

    private boolean validateDuplication(final long memberId, final long channelId) {
        return subscriptionRepository.existsByMemberIdAndChannelId(memberId, channelId);
    }
}
