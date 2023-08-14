package youtube.application.subscription.command;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.ChannelCache;
import youtube.repository.channel.ChannelRepository;

import java.util.concurrent.CompletableFuture;

@Transactional
@Service
public class CommandSubscribersUpdate {

    private final ChannelRepository channelRepository;

    public CommandSubscribersUpdate(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    // 캐시를 DB와 동기화하는 작업 비동기로 실행 - 스케줄러 안에서 사용되는 메소드
    @Async
    public CompletableFuture<Void> command(final long channelId, final ChannelCache channelCache) {
        Channel entity = channelRepository.getById(channelId);
        int subscribersCount = channelCache.getSubscribersCount();
        entity.updateSubscribersCount(subscribersCount);
        return CompletableFuture.completedFuture(null);
    }
}