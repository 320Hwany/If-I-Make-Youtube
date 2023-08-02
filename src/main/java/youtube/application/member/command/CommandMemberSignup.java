package youtube.application.member.command;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import youtube.domain.member.persist.Member;
import youtube.domain.member.persist.MemberRepository;
import youtube.exception.member.MemberDuplicationException;
import youtube.mapper.member.MemberMapper;
import youtube.mapper.member.dto.MemberSignupRequest;

@Transactional
@Service
public class CommandMemberSignup {

    private final MemberRepository memberRepository;

    public CommandMemberSignup(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void command(final MemberSignupRequest dto) {
        boolean isPresent = memberRepository.existsByNicknameOrLoginId(dto.nickname(), dto.loginId());
        if (isPresent) {
            throw new MemberDuplicationException();
        }
        Member entity = MemberMapper.toEntity(dto);
        memberRepository.save(entity);
    }
}
