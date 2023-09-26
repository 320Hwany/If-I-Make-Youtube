package youtube.application.channel.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.repository.channel.ChannelRepository;

import java.util.List;

@Service
public class ChannelFindAllReader {

    private final ChannelRepository channelRepository;

    public ChannelFindAllReader(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Transactional(readOnly = true)
    public List<Channel> query() {
        return channelRepository.findAll();
    }
}
