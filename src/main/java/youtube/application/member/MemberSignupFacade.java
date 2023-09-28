package youtube.application.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.domain.member.persist.Member;
import youtube.global.exception.BadRequestException;
import youtube.mapper.channel.ChannelMapper;
import youtube.mapper.member.MemberMapper;
import youtube.mapper.member.dto.MemberSignupRequest;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.member.MemberRepository;

import static youtube.global.constant.ExceptionMessageConstant.MEMBER_DUPLICATION;


@Service
public class MemberSignupFacade {

    private final MemberRepository memberRepository;
    private final ChannelRepository channelRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberSignupFacade(final MemberRepository memberRepository,
                              final ChannelRepository channelRepository,
                              final PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.channelRepository = channelRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입한 신규회원은 자동으로 채널 하나 생성
    @Transactional
    public void signup(final MemberSignupRequest dto) {
        boolean isPresent = memberRepository.existsByNicknameOrLoginId(dto.nickname(), dto.loginId());
        if (isPresent) {
            throw new BadRequestException(MEMBER_DUPLICATION.message);
        }
        Member member = MemberMapper.toEntity(dto, passwordEncoder);
        memberRepository.save(member);

        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);
    }
}
