package youtube.mapper.subscription.dto;

public record SubscriptionEvent(
        long memberId,
        long channelId
) {
}
