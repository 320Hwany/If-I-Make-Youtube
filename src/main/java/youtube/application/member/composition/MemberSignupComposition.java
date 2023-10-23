package youtube.application.member.composition;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.application.channel.ChannelCreator;
import youtube.application.member.MemberCreator;
import youtube.domain.member.persist.Member;
import youtube.mapper.member.dto.MemberSignupRequest;


@Service
public class MemberSignupComposition {

    private final MemberCreator memberCreator;
    private final ChannelCreator channelCreator;

    public MemberSignupComposition(final MemberCreator memberCreator, final ChannelCreator channelCreator) {
        this.memberCreator = memberCreator;
        this.channelCreator = channelCreator;
    }

    // 회원가입한 신규회원은 자동으로 채널 하나 생성
    @Transactional
    public void signup(final MemberSignupRequest dto) {
        Member member = memberCreator.create(dto);
        channelCreator.create(member);
    }
}
