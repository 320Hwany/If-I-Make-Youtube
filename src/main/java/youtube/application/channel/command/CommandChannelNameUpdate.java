package youtube.application.channel.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.ChannelName;
import youtube.repository.channel.ChannelRepository;

@Transactional
@Service
public class CommandChannelNameUpdate {

    private final ChannelRepository channelRepository;

    public CommandChannelNameUpdate(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public void command(final long memberId, final ChannelName channelNameUpdate) {
        Channel entity = channelRepository.getByMemberId(memberId);
        ChannelName channelName = entity.getChannelName();
        channelName.update(channelNameUpdate);
    }
}
