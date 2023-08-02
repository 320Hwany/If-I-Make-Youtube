package youtube.application.member.command;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.member.persist.Member;
import youtube.domain.member.persist.MemberRepository;
import youtube.exception.member.MemberDuplicationException;
import youtube.mapper.member.MemberMapper;
import youtube.mapper.member.dto.MemberSignupRequest;

@Transactional
@Service
public class CommandMemberSignup {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public CommandMemberSignup(final MemberRepository memberRepository,
                               final PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void command(final MemberSignupRequest dto) {
        boolean isPresent = memberRepository.existsByNicknameOrLoginId(dto.nickname(), dto.loginId());
        if (isPresent) {
            throw new MemberDuplicationException();
        }
        Member entity = MemberMapper.toEntity(dto, passwordEncoder);
        memberRepository.save(entity);
    }
}
