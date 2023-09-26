package youtube.application.member.command;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.member.persist.Member;
import youtube.global.exception.BadRequestException;
import youtube.mapper.member.MemberMapper;
import youtube.mapper.member.dto.MemberSignupRequest;
import youtube.repository.member.MemberRepository;

import static youtube.global.constant.ExceptionMessageConstant.MEMBER_DUPLICATION;

@Service
public class MemberCreator {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberCreator(final MemberRepository memberRepository, final PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Member command(final MemberSignupRequest dto) {
        boolean isPresent = memberRepository.existsByNicknameOrLoginId(dto.nickname(), dto.loginId());
        if (isPresent) {
            throw new BadRequestException(MEMBER_DUPLICATION.message);
        }
        Member entity = MemberMapper.toEntity(dto, passwordEncoder);
        memberRepository.save(entity);
        return entity;
    }
}
