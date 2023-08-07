package youtube.application.member.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.member.persist.Member;
import youtube.domain.member.persist.MemberRepository;
import youtube.domain.member.vo.LoginId;

@Transactional(readOnly = true)
@Service
public class QueryMemberByLoginId {

    private final MemberRepository memberRepository;

    public QueryMemberByLoginId(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getByLoginId(final LoginId loginId) {
        return memberRepository.getByLoginId(loginId);
    }
}
