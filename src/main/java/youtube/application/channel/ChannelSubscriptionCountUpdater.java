package youtube.application.channel;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.ChannelCache;
import youtube.repository.channel.ChannelRepository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ChannelSubscriptionCountUpdater {

    private final ChannelRepository channelRepository;

    public ChannelSubscriptionCountUpdater(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    // 캐시를 DB와 동기화하는 작업 비동기로 실행 - 스케줄러 안에서 사용되는 메소드
    @Async
    @Transactional
    public CompletableFuture<Void> command(final long channelId, final ChannelCache channelCache) {
        Channel entity = channelRepository.getById(channelId);
        AtomicInteger atomicSubscribersCount = channelCache.getSubscribersCount();
        int subscribersCount = atomicSubscribersCount.get();
        entity.updateSubscribersCount(subscribersCount);
        return CompletableFuture.completedFuture(null);
    }
}
