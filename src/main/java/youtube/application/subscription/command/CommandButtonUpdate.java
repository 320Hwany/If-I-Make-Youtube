package youtube.application.subscription.command;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.Button;
import youtube.repository.channel.ChannelRepository;


@Service
public class CommandButtonUpdate {

    private final ChannelRepository channelRepository;

    public CommandButtonUpdate(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Async
    @Transactional
    public void command(final Channel entity) {
        Button button = Button.from(entity.getSubscribersCount());
        entity.updateButton(button);
    }
}
