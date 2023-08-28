package youtube.application.subscription;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import youtube.application.channel.query.QueryChannelCacheById;
import youtube.domain.channel.vo.ChannelCache;
import youtube.mapper.subscription.dto.SubscriptionCancelEvent;
import youtube.mapper.subscription.dto.SubscriptionSaveEvent;

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
    @EventListener(classes = {SubscriptionSaveEvent.class})
    public void subscriptionSaveEvent(final SubscriptionSaveEvent subscriptionSaveEvent) {
        long channelId = subscriptionSaveEvent.channelId();
        long memberId = subscriptionSaveEvent.memberId();

        ChannelCache channelCache = queryChannelCacheById.query(channelId);
        subscribersCountService.increaseCount(channelId, channelCache);
        subscriptionEvictService.clearCacheByMemberId(memberId);
    }


    // CommandSubscriptionCancel - 채널 구독 취소시 발생하는 이벤트
    @EventListener(classes = {SubscriptionCancelEvent.class})
    public void subscriptionCancelEvent(final SubscriptionCancelEvent subscriptionCancelEvent) {
        long channelId = subscriptionCancelEvent.channelId();
        long memberId = subscriptionCancelEvent.memberId();

        ChannelCache channelCache = queryChannelCacheById.query(channelId);
        subscribersCountService.decreaseCount(channelId, channelCache);
        subscriptionEvictService.clearCacheByMemberId(memberId);
    }
}
