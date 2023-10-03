package youtube.domain.channel.vo;

import lombok.Builder;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public final class ChannelCache {

    private ChannelName channelName;

    private ChannelDescription channelDescription;

    private int videosCount;

    private AtomicInteger subscribersCount;

    @Builder
    private ChannelCache(final ChannelName channelName, final ChannelDescription channelDescription,
                        final int videosCount, final AtomicInteger subscribersCount) {
        this.channelName = channelName;
        this.channelDescription = channelDescription;
        this.videosCount = videosCount;
        this.subscribersCount = subscribersCount;
    }

    public void increaseSubscribersCount() {
        subscribersCount.incrementAndGet();
    }

    public void decreaseSubscribersCount() {
        subscribersCount.decrementAndGet();
    }

    public void updateChannelName(final ChannelName channelName) {
        this.channelName = channelName;
    }

    public void updateChannelDescription(final ChannelDescription channelDescription) {
        this.channelDescription = channelDescription;
    }
}
