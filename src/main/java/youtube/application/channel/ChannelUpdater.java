package youtube.application.channel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;
import youtube.repository.channel.ChannelRepository;

@Service
public class ChannelUpdater {

    private final ChannelRepository channelRepository;

    public ChannelUpdater(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Transactional
    public long updateChannelName(final long memberId, final ChannelName channelNameUpdate) {
        Channel entity = channelRepository.getByMemberId(memberId);
        ChannelName channelName = entity.getChannelName();
        channelName.update(channelNameUpdate);
        return entity.getId();
    }

    @Transactional
    public long updateChannelDescription(final long memberId, final ChannelDescription channelDescriptionUpdate) {
        Channel entity = channelRepository.getByMemberId(memberId);
        ChannelDescription channelDescription = entity.getChannelDescription();
        channelDescription.update(channelDescriptionUpdate);
        return entity.getId();
    }
}
