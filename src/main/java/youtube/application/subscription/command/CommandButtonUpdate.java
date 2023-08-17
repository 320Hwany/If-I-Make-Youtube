package youtube.application.subscription.command;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import youtube.domain.channel.persist.Channel;
import youtube.repository.channel.ChannelRepository;

@Service
public class CommandButtonUpdate {

    private final ChannelRepository channelRepository;

    public CommandButtonUpdate(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Async
    public void command(final long channelId) {

    }
}
