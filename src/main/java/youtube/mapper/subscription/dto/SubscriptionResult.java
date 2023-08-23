package youtube.mapper.subscription.dto;


import java.util.List;

public record SubscriptionResult(
        List<SubscriptionChannelsCache> subscriptionChannelsCaches,
        int channelCount
) {
}
