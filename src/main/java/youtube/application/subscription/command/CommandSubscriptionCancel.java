package youtube.application.subscription.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.application.channel.query.QueryChannelCacheById;
import youtube.application.subscription.SubscribersCountService;
import youtube.domain.channel.vo.ChannelCache;
import youtube.domain.subscription.Subscription;
import youtube.repository.subscription.SubscriptionRepository;

@Service
public class CommandSubscriptionCancel {

    private final SubscriptionRepository subscriptionRepository;
    private final QueryChannelCacheById queryChannelCacheById;
    private final SubscribersCountService subscribersCountService;

    public CommandSubscriptionCancel(final SubscriptionRepository subscriptionRepository,
                                     final QueryChannelCacheById queryChannelCacheById,
                                     final SubscribersCountService subscribersCountService) {
        this.subscriptionRepository = subscriptionRepository;
        this.queryChannelCacheById = queryChannelCacheById;
        this.subscribersCountService = subscribersCountService;
    }

    @Transactional
    public void command(final long memberId, final long channelId) {
        Subscription entity = subscriptionRepository.getByMemberIdAndChannelId(memberId, channelId);
        subscriptionRepository.delete(entity);

        ChannelCache channelCache = queryChannelCacheById.query(channelId);
        subscribersCountService.decreaseCount(channelId, channelCache);
    }
}
