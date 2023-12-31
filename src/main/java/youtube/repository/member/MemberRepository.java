package youtube.repository.member;

import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.mapper.member.dto.MemberDetailedResponse;

public interface MemberRepository {

    void save(final Member member);

    Member getByLoginId(final LoginId loginId);

    Member getById(final long memberId);

    Nickname getNicknameById(final long memberId);

    MemberDetailedResponse getDetailedResponseById(final long memberId);

    boolean existsByNicknameOrLoginId(final Nickname nickname, final LoginId loginId);

    long count();
}
