package youtube.application.channel.business;

import org.springframework.stereotype.Service;
import youtube.application.channel.implement.ChannelUpdater;
import youtube.application.channel.implement.ChannelCacheReader;
import youtube.domain.channel.vo.ChannelCache;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;

@Service
public class ChannelUpdaterBusiness {

    private final ChannelUpdater channelUpdater;
    private final ChannelCacheReader channelCacheReader;

    public ChannelUpdaterBusiness(final ChannelUpdater channelUpdater, final ChannelCacheReader channelCacheReader) {
        this.channelUpdater = channelUpdater;
        this.channelCacheReader = channelCacheReader;
    }

    public void updateChannelName(final long memberId, final ChannelName channelName) {
        long channelId = channelUpdater.updateChannelName(memberId, channelName);
        updateChannelNameCache(channelId, channelName);
    }

    public void updateChannelDescription(final long memberId, final ChannelDescription channelDescription) {
        long channelId = channelUpdater.updateChannelDescription(memberId, channelDescription);
        updateChannelDescriptionCache(channelId, channelDescription);
    }

    private void updateChannelNameCache(final long channelId, final ChannelName channelName) {
        ChannelCache channelCache = channelCacheReader.getByChannelId(channelId);
        channelCache.updateChannelName(channelName);
    }

    private void updateChannelDescriptionCache(final long channelId,
                                               final ChannelDescription channelDescription) {
        ChannelCache channelCache = channelCacheReader.getByChannelId(channelId);
        channelCache.updateChannelDescription(channelDescription);
    }
}