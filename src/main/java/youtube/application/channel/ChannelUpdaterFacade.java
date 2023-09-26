package youtube.application.channel;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import youtube.application.channel.command.ChannelUpdater;
import youtube.application.channel.query.ChannelCacheReader;
import youtube.domain.channel.vo.ChannelCache;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;

import static youtube.global.constant.AnnotationMessageConstant.CHANNEL_CACHE;

@Service
public class ChannelUpdaterFacade {

    private final ChannelUpdater channelUpdater;
    private final ChannelCacheReader channelCacheReader;
    private final CacheManager cacheManager;

    public ChannelUpdaterFacade(final youtube.application.channel.command.ChannelUpdater channelUpdater,
                                final ChannelCacheReader channelCacheReader,
                                final CacheManager cacheManager) {
        this.channelUpdater = channelUpdater;
        this.channelCacheReader = channelCacheReader;
        this.cacheManager = cacheManager;
    }

    public void updateChannelName(final long memberId, final ChannelName channelName) {
        long channelId = channelUpdater.updateChannelName(memberId, channelName);
        updateChannelNameCache(channelId, channelName);
    }

    public void updateChannelDescription(final long memberId, final ChannelDescription channelDescription) {
        long channelId = channelUpdater.updateChannelDescription(memberId, channelDescription);
        updateChannelDescriptionCache(channelId, channelDescription);
    }

    private synchronized void updateChannelNameCache(final long channelId, final ChannelName channelName) {
        ChannelCache channelCache = channelCacheReader.query(channelId);
        channelCache.updateChannelName(channelName);
        Cache cache = cacheManager.getCache(CHANNEL_CACHE);
        assert cache != null;
        cache.put(channelId, channelCache);
    }

    private synchronized void updateChannelDescriptionCache(final long channelId,
                                                           final ChannelDescription channelDescription) {
        ChannelCache channelCache = channelCacheReader.query(channelId);
        channelCache.updateChannelDescription(channelDescription);
        Cache cache = cacheManager.getCache(CHANNEL_CACHE);
        assert cache != null;
        cache.put(channelId, channelCache);
    }
}
