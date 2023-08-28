package youtube.application.channel.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.domain.member.persist.Member;
import youtube.mapper.channel.ChannelMapper;
import youtube.repository.channel.ChannelRepository;

@Service
public class CommandChannelSave {

    private final ChannelRepository channelRepository;

    public CommandChannelSave(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    // 회원 가입시에만 실행되는 메소드 - MemberSignupService 내부에서만 호출
    @Transactional
    public void command(final Member member) {
        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);
    }
}
