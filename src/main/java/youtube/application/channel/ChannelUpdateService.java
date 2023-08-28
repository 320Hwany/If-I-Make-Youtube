package youtube.application.channel;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import youtube.application.channel.command.CommandChannelUpdate;
import youtube.application.channel.query.QueryChannelCacheById;
import youtube.domain.channel.vo.ChannelCache;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;

import static youtube.global.constant.AnnotationMessageConstant.CHANNEL_CACHE;

@Service
public class ChannelUpdateService {

    private final CommandChannelUpdate commandChannelUpdate;
    private final QueryChannelCacheById queryChannelCacheById;
    private final CacheManager cacheManager;

    public ChannelUpdateService(final CommandChannelUpdate commandChannelUpdate,
                                final QueryChannelCacheById queryChannelCacheById,
                                final CacheManager cacheManager) {
        this.commandChannelUpdate = commandChannelUpdate;
        this.queryChannelCacheById = queryChannelCacheById;
        this.cacheManager = cacheManager;
    }

    public void updateChannelName(final long memberId, final ChannelName channelName) {
        long channelId = commandChannelUpdate.updateChannelName(memberId, channelName);
        updateChannelNameCache(channelId, channelName);
    }

    public void updateChannelDescription(final long memberId, final ChannelDescription channelDescription) {
        long channelId = commandChannelUpdate.updateChannelDescription(memberId, channelDescription);
        updateChannelDescriptionCache(channelId, channelDescription);
    }

    private synchronized void updateChannelNameCache(final long channelId, final ChannelName channelName) {
        ChannelCache channelCache = queryChannelCacheById.query(channelId);
        channelCache.updateChannelName(channelName);
        Cache cache = cacheManager.getCache(CHANNEL_CACHE);
        assert cache != null;
        cache.put(channelId, channelCache);
    }

    private synchronized void updateChannelDescriptionCache(final long channelId,
                                                           final ChannelDescription channelDescription) {
        ChannelCache channelCache = queryChannelCacheById.query(channelId);
        channelCache.updateChannelDescription(channelDescription);
        Cache cache = cacheManager.getCache(CHANNEL_CACHE);
        assert cache != null;
        cache.put(channelId, channelCache);
    }
}
