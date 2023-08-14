package youtube.application.subscription.command;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.mapper.channel.dto.ChannelCache;
import youtube.repository.channel.ChannelRepository;

import java.util.concurrent.CompletableFuture;

@Transactional
@Service
public class CommandSubscribersUpdate {

    private final ChannelRepository channelRepository;

    public CommandSubscribersUpdate(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Async
    public CompletableFuture<Void> command(final long channelId, final ChannelCache channelCache) {
        Channel entity = channelRepository.getById(channelId);
        int subscribersCount = channelCache.getSubscribersCount();
        entity.updateSubscribersCount(subscribersCount);
        return CompletableFuture.completedFuture(null);
    }
}
