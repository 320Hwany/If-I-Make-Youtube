package youtube.domain.member.persist;

import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;

public interface MemberRepository {

    void save(final Member member);

    boolean existsByNicknameOrLoginId(final Nickname nickname, final LoginId loginId);
}
