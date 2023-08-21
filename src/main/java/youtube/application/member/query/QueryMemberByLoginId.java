package youtube.application.member.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.member.persist.Member;
import youtube.repository.member.MemberRepository;
import youtube.domain.member.vo.LoginId;

@Service
public class QueryMemberByLoginId {

    private final MemberRepository memberRepository;

    public QueryMemberByLoginId(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public Member query(final LoginId loginId) {
        return memberRepository.getByLoginId(loginId);
    }
}
