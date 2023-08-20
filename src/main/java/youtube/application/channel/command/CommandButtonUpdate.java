package youtube.application.channel.command;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.Button;
import youtube.repository.channel.ChannelRepository;

import java.util.concurrent.CompletableFuture;


@Service
public class CommandButtonUpdate {

    private final ChannelRepository channelRepository;

    public CommandButtonUpdate(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Async
    @Transactional
    public CompletableFuture<Void> command(final long channelId) {
        Channel entity = channelRepository.getById(channelId);
        Button button = Button.from(entity.getSubscribersCount());
        entity.updateButton(button);
        return CompletableFuture.completedFuture(null);
    }
}
