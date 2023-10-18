package youtube.application.subscription;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import youtube.application.channel.implement.ChannelCacheReader;
import youtube.application.subscription.implement.SubscriptionsCacheEvictor;
import youtube.domain.channel.vo.ChannelCache;
import youtube.mapper.subscription.dto.SubscriptionCancelEvent;
import youtube.mapper.subscription.dto.SubscriptionSaveEvent;

@Service
public class SubscriptionsEventListener {

    private final SubscriptionsCacheEvictor subscriptionsCacheEvictor;
    private final ChannelCacheReader channelCacheReader;

    public SubscriptionsEventListener(final SubscriptionsCacheEvictor subscriptionsCacheEvictor,
                                      final ChannelCacheReader channelCacheReader) {
        this.subscriptionsCacheEvictor = subscriptionsCacheEvictor;
        this.channelCacheReader = channelCacheReader;
    }

    // SubscriptionsCreator - 채널 구독시 발생하는 이벤트
    @EventListener(classes = {SubscriptionSaveEvent.class})
    public void subscriptionSaveEvent(final SubscriptionSaveEvent subscriptionSaveEvent) {
        long channelId = subscriptionSaveEvent.channelId();
        long memberId = subscriptionSaveEvent.memberId();

        ChannelCache channelCache = channelCacheReader.getByChannelId(channelId);
        channelCache.increaseSubscribersCount();;
        subscriptionsCacheEvictor.clearCacheByMemberId(memberId);
    }


    // SubscriptionsDeleter - 채널 구독 취소시 발생하는 이벤트
    @EventListener(classes = {SubscriptionCancelEvent.class})
    public void subscriptionCancelEvent(final SubscriptionCancelEvent subscriptionCancelEvent) {
        long channelId = subscriptionCancelEvent.channelId();
        long memberId = subscriptionCancelEvent.memberId();

        ChannelCache channelCache = channelCacheReader.getByChannelId(channelId);
        channelCache.decreaseSubscribersCount();
        subscriptionsCacheEvictor.clearCacheByMemberId(memberId);
    }
}
