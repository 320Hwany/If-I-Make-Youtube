package youtube.application.channel;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import youtube.application.channel.command.CommandChannelSubscriptionCountUpdate;
import youtube.domain.channel.vo.ChannelCache;
import youtube.global.exception.BadRequestException;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static youtube.global.constant.AnnotationMessageConstant.*;
import static youtube.global.constant.ExceptionMessageConstant.*;

@Service
public class ChannelCacheSyncService {

    private final CommandChannelSubscriptionCountUpdate commandChannelSubscriptionCountUpdate;
    private final CacheManager cacheManager;

    public ChannelCacheSyncService(final CommandChannelSubscriptionCountUpdate commandChannelSubscriptionCountUpdate,
                                   final CacheManager cacheManager) {
        this.commandChannelSubscriptionCountUpdate = commandChannelSubscriptionCountUpdate;
        this.cacheManager = cacheManager;
    }

    // 1분에 한번씩 채널 정보 캐싱한 데이터를 DB와 동기화
    @Async
    @Scheduled(fixedRate = ONE_MINUTE)
    public void syncChannelCache() {
        Map<Long, ChannelCache> cacheMap = getChannelCacheMap();

        CompletableFuture<?>[] futures = cacheMap.entrySet()
                .stream()
                .map(entry ->
                        CompletableFuture.runAsync(
                                () -> commandChannelSubscriptionCountUpdate.command(entry.getKey(), entry.getValue())
                        )
                )
                .toArray(CompletableFuture[]::new);

        CompletableFuture<Void> result = CompletableFuture.allOf(futures);
        validateResult(result);
    }

    private Map<Long, ChannelCache> getChannelCacheMap() {
        Cache cache = cacheManager.getCache(CHANNEL_CACHE);
        assert cache != null;
        try {
            //noinspection unchecked
            return (Map<Long, ChannelCache>) cache.getNativeCache();
        } catch (ClassCastException e) {
            throw new BadRequestException(CLASS_CAST_BAD_REQUEST.message);
        }
    }

    private void validateResult(final CompletableFuture<Void> result) {
        try {
            result.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new BadRequestException(SUBSCRIBERS_SCHEDULER_BAD_REQUEST.message);
        }
    }
}
