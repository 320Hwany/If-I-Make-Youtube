package youtube.application.subscription;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import youtube.application.subscription.command.CommandSubscribersUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static youtube.global.constant.AnnotationMessageConstant.*;
import static youtube.global.constant.CacheConstant.SUBSCRIBERS_COUNT;

@Service
public class SubscribersSyncService {

    private final CommandSubscribersUpdate commandSubscribersUpdate;
    private final CacheManager cacheManager;

    public SubscribersSyncService(final CommandSubscribersUpdate commandSubscribersUpdate,
                                  final CacheManager cacheManager) {
        this.commandSubscribersUpdate = commandSubscribersUpdate;
        this.cacheManager = cacheManager;
    }

    @Async
    @Scheduled(fixedRate = ONE_MINUTE)
    public void syncSubscribersCount() {
        Cache cache = cacheManager.getCache(SUBSCRIBERS_COUNT);
        assert cache != null;

        Map<Long, Integer> cacheMap = (Map<Long, Integer>) cache.getNativeCache();
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        cacheMap.forEach((channelId, subscribersCount) ->
                futures.add(commandSubscribersUpdate.command(channelId, subscribersCount)));

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }
}
