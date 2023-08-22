package youtube.mapper.subscription.dto;

import com.querydsl.core.annotations.QueryProjection;
import youtube.domain.channel.vo.ChannelName;

public record SubscriptionChannelDto(
        long channelId,
        ChannelName channelName
) {
    @QueryProjection
    public SubscriptionChannelDto {
    }
}
