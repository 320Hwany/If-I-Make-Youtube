package youtube.domain.member.persist;

import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.mapper.member.dto.MemberDetailedResponse;

public interface MemberRepository {

    void save(final Member member);

    Member getByLoginId(final LoginId loginId);

    Member getById(final long memberId);

    MemberDetailedResponse getDetailedResponseById(final long memberId);

    boolean existsByNicknameOrLoginId(final Nickname nickname, final LoginId loginId);

    long count();
}
