package youtube.mapper.channel.dto;

import com.querydsl.core.annotations.QueryProjection;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;

public record ChannelCacheDto(
        ChannelName channelName,
        ChannelDescription channelDescription,
        int videosCount,
        int subscribersCount
) {
    @QueryProjection
    public ChannelCacheDto {
    }
}
