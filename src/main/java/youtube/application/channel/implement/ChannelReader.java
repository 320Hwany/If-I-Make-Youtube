package youtube.application.channel.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.repository.channel.ChannelRepository;

import java.util.List;

@Service
public class ChannelReader {

    private final ChannelRepository channelRepository;

    public ChannelReader(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Transactional(readOnly = true)
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }
}
