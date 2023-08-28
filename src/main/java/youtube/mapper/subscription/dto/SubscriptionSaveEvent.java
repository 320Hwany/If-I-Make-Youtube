package youtube.mapper.subscription.dto;

public record SubscriptionSaveEvent(
        long memberId,
        long channelId
) {
}
