package youtube.mapper.channel.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChannelCache {

    private ChannelName channelName;

    private ChannelDescription channelDescription;

    private int videosCount;

    private int subscribersCount;

    @QueryProjection
    public ChannelCache(final ChannelName channelName, final ChannelDescription channelDescription,
                        final int videosCount, final int subscribersCount) {
        this.channelName = channelName;
        this.channelDescription = channelDescription;
        this.videosCount = videosCount;
        this.subscribersCount = subscribersCount;
    }
}
