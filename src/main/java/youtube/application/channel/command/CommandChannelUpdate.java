package youtube.application.channel.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;
import youtube.repository.channel.ChannelRepository;

@Transactional
@Service
public class CommandChannelUpdate {

    private final ChannelRepository channelRepository;

    public CommandChannelUpdate(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public void updateChannelName(final long memberId, final ChannelName channelNameUpdate) {
        Channel entity = channelRepository.getByMemberId(memberId);
        ChannelName channelName = entity.getChannelName();
        channelName.update(channelNameUpdate);
    }

    public void updateChannelDescription(final long memberId, final ChannelDescription channelDescriptionUpdate) {
        Channel entity = channelRepository.getByMemberId(memberId);
        ChannelDescription channelDescription = entity.getChannelDescription();
        channelDescription.update(channelDescriptionUpdate);
    }
}
