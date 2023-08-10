package youtube.mapper.subscription;

import youtube.domain.subscription.Subscription;

public class SubscriptionMapper {

    private SubscriptionMapper() {
    }

    public static Subscription toEntity(final long memberId, final long channelId) {
        return Subscription.builder()
                .memberId(memberId)
                .channelId(channelId)
                .isNotification(false)
                .build();
    }
}
