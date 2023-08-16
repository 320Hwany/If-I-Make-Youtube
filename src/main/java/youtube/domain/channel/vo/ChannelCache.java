package youtube.domain.channel.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static youtube.global.constant.NumberConstant.ONE;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class ChannelCache {

    private ChannelName channelName;

    private ChannelDescription channelDescription;

    private int videosCount;

    private int subscribersCount;

    @Builder
    private ChannelCache(final ChannelName channelName, final ChannelDescription channelDescription,
                        final int videosCount, final int subscribersCount) {
        this.channelName = channelName;
        this.channelDescription = channelDescription;
        this.videosCount = videosCount;
        this.subscribersCount = subscribersCount;
    }

    public void increaseSubscribersCount() {
        this.subscribersCount += ONE.value;
    }

    public void decreaseSubscribersCount() {
        this.subscribersCount -= ONE.value;
    }
}
