package youtube.application.subscription.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.application.channel.query.QueryChannelCache;
import youtube.application.subscription.SubscribersCountService;
import youtube.domain.subscription.Subscription;
import youtube.global.exception.BadRequestException;
import youtube.domain.channel.vo.ChannelCache;
import youtube.mapper.subscription.SubscriptionMapper;
import youtube.repository.subscription.SubscriptionRepository;

import static youtube.global.constant.ExceptionMessageConstant.*;


@Service
public class CommandSubscribe {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscribersCountService subscribersCountService;
    private final QueryChannelCache queryChannelCache;

    public CommandSubscribe(final SubscriptionRepository subscriptionRepository,
                            final SubscribersCountService subscribersCountService,
                            final QueryChannelCache queryChannelCache) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscribersCountService = subscribersCountService;
        this.queryChannelCache = queryChannelCache;
    }

    @Transactional
    public void command(final long memberId, final long channelId) {
        if (validateDuplication(memberId, channelId)) {
            throw new BadRequestException(SUBSCRIBE_DUPLICATION.message);
        }
        Subscription subscription = SubscriptionMapper.toEntity(memberId, channelId);
        subscriptionRepository.save(subscription);

        ChannelCache channelCache = queryChannelCache.getCache(channelId);
        subscribersCountService.increaseCount(channelId, channelCache);
    }

    private boolean validateDuplication(final long memberId, final long channelId) {
        return subscriptionRepository.existsByMemberIdAndChannelId(memberId, channelId);
    }
}