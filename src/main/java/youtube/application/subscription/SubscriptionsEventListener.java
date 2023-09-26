package youtube.application.subscription;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import youtube.application.channel.query.ChannelCacheReader;
import youtube.domain.channel.vo.ChannelCache;
import youtube.mapper.subscription.dto.SubscriptionCancelEvent;
import youtube.mapper.subscription.dto.SubscriptionSaveEvent;

@Service
public class SubscriptionsEventListener {

    private final SubscriptionsCacheEvictor subscriptionsCacheEvictor;
    private final ChannelCacheReader channelCacheReader;
    private final SubscribersCounter subscribersCounter;

    public SubscriptionsEventListener(final SubscriptionsCacheEvictor subscriptionsCacheEvictor,
                                      final ChannelCacheReader channelCacheReader,
                                      final SubscribersCounter subscribersCounter) {
        this.subscriptionsCacheEvictor = subscriptionsCacheEvictor;
        this.channelCacheReader = channelCacheReader;
        this.subscribersCounter = subscribersCounter;
    }

    // SubscriptionsCreator - 채널 구독시 발생하는 이벤트
    @EventListener(classes = {SubscriptionSaveEvent.class})
    public void subscriptionSaveEvent(final SubscriptionSaveEvent subscriptionSaveEvent) {
        long channelId = subscriptionSaveEvent.channelId();
        long memberId = subscriptionSaveEvent.memberId();

        ChannelCache channelCache = channelCacheReader.getByChannelId(channelId);
        subscribersCounter.increaseCount(channelId, channelCache);
        subscriptionsCacheEvictor.clearCacheByMemberId(memberId);
    }


    // SubscriptionsDeleter - 채널 구독 취소시 발생하는 이벤트
    @EventListener(classes = {SubscriptionCancelEvent.class})
    public void subscriptionCancelEvent(final SubscriptionCancelEvent subscriptionCancelEvent) {
        long channelId = subscriptionCancelEvent.channelId();
        long memberId = subscriptionCancelEvent.memberId();

        ChannelCache channelCache = channelCacheReader.getByChannelId(channelId);
        subscribersCounter.decreaseCount(channelId, channelCache);
        subscriptionsCacheEvictor.clearCacheByMemberId(memberId);
    }
}
