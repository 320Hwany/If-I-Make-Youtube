package youtube.application.member.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.repository.member.MemberRepository;
import youtube.mapper.member.dto.MemberDetailedResponse;

@Transactional(readOnly = true)
@Service
public class QueryMemberDetailedResponse {

    private final MemberRepository memberRepository;

    public QueryMemberDetailedResponse(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDetailedResponse query(final long memberId) {
        return memberRepository.getDetailedResponseById(memberId);
    }
}
