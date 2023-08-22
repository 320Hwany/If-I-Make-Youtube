package youtube.application.subscription;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import youtube.application.channel.query.QueryChannelCacheById;
import youtube.domain.channel.vo.ChannelCache;
import youtube.mapper.subscription.dto.SubscriptionEvent;

@Service
public class SubscriptionEventListener {

    private final SubscriptionEvictService subscriptionEvictService;
    private final QueryChannelCacheById queryChannelCacheById;
    private final SubscribersCountService subscribersCountService;

    public SubscriptionEventListener(final SubscriptionEvictService subscriptionEvictService,
                                     final QueryChannelCacheById queryChannelCacheById,
                                     final SubscribersCountService subscribersCountService) {
        this.subscriptionEvictService = subscriptionEvictService;
        this.queryChannelCacheById = queryChannelCacheById;
        this.subscribersCountService = subscribersCountService;
    }

    // CommandSubscriptionSave - 채널 구독시 발생하는 이벤트
    @EventListener(classes = {SubscriptionEvent.class})
    public void event(final SubscriptionEvent subscriptionEvent) {
        long channelId = subscriptionEvent.channelId();
        long memberId = subscriptionEvent.memberId();

        ChannelCache channelCache = queryChannelCacheById.query(channelId);
        subscribersCountService.increaseCount(channelId, channelCache);
        subscriptionEvictService.clearCacheByMemberId(memberId);
    }
}
