package youtube.mapper.subscription.dto;

import youtube.domain.channel.vo.ChannelName;

public record SubscriptionChannelsCache(
        long channelId,
        ChannelName channelName
) {
}
