package youtube.mapper.subscription.dto;

public record SubscriptionCancelEvent(
        long memberId,
        long channelId
) {
}
