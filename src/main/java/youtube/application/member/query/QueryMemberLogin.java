package youtube.application.member.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.member.persist.MemberRepository;

@Transactional(readOnly = true)
@Service
public class QueryMemberLogin {

    private final MemberRepository memberRepository;

    public QueryMemberLogin(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
