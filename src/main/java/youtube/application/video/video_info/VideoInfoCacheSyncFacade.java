package youtube.application.video.video_info;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import youtube.application.video.video_info.command.VideoInfoCacheUpdater;
import youtube.domain.video.video_info.vo.VideoInfoCache;
import youtube.global.exception.BadRequestException;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static youtube.global.constant.AnnotationMessageConstant.ONE_MINUTE;
import static youtube.global.constant.AnnotationMessageConstant.VIDEO_INFO_CACHE;
import static youtube.global.constant.ExceptionMessageConstant.*;

@Service
public class VideoInfoCacheSyncFacade {

    private final VideoInfoCacheUpdater videoInfoCacheUpdater;
    private final CacheManager cacheManager;

    public VideoInfoCacheSyncFacade(final VideoInfoCacheUpdater videoInfoCacheUpdater,
                                    final CacheManager cacheManager) {
        this.videoInfoCacheUpdater = videoInfoCacheUpdater;
        this.cacheManager = cacheManager;
    }

    // 1분에 한번씩 동영상 정보 캐싱한 데이터를 DB와 동기화
    @Async
    @Scheduled(fixedRate = ONE_MINUTE)
    public void syncVideoInfoCache() {
        Map<Long, VideoInfoCache> cacheMap = getVideoInfoCacheMap();

        CompletableFuture<?>[] futures = cacheMap.entrySet()
                .stream()
                .map(entry ->
                        CompletableFuture.runAsync(
                                () -> videoInfoCacheUpdater.command(entry.getKey(), entry.getValue())
                        )
                )
                .toArray(CompletableFuture[]::new);

        CompletableFuture<Void> result = CompletableFuture.allOf(futures);
        validateResult(result);
    }

    private Map<Long, VideoInfoCache> getVideoInfoCacheMap() {
        Cache cache = cacheManager.getCache(VIDEO_INFO_CACHE);
        assert cache != null;
        try {
            //noinspection unchecked
            return (Map<Long, VideoInfoCache>) cache.getNativeCache();
        } catch (ClassCastException e) {
            throw new BadRequestException(CLASS_CAST_BAD_REQUEST.message);
        }
    }

    private void validateResult(final CompletableFuture<Void> result) {
        try {
            result.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new BadRequestException(VIDEO_INFO_SCHEDULER_BAD_REQUEST.message);
        }
    }
}
