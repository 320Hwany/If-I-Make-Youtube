package youtube.application.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.member.persist.Member;
import youtube.mapper.member.dto.MemberDetailedResponse;
import youtube.repository.member.MemberRepository;
import youtube.domain.member.vo.LoginId;

@Service
public class MemberReader {

    private final MemberRepository memberRepository;

    public MemberReader(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public Member getByLoginId(final LoginId loginId) {
        return memberRepository.getByLoginId(loginId);
    }

    @Transactional(readOnly = true)
    public MemberDetailedResponse getMemberResponseById(final long memberId) {
        return memberRepository.getDetailedResponseById(memberId);
    }
}
