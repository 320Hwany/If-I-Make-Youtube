package youtube.application.channel.query;

import org.springframework.stereotype.Service;
import youtube.repository.channel.ChannelRepository;

@Service
public class QueryChannelResponseById {

    private final ChannelRepository channelRepository;

    public QueryChannelResponseById(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

}
