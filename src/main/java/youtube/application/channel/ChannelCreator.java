package youtube.application.channel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.domain.member.persist.Member;
import youtube.mapper.channel.ChannelMapper;
import youtube.repository.channel.ChannelRepository;

@Service
public class ChannelCreator {

    private final ChannelRepository channelRepository;

    public ChannelCreator(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Transactional
    public void create(final Member member) {
        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);
    }
}
