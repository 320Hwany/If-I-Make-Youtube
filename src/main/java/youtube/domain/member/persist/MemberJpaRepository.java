package youtube.domain.member.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    boolean existsByNicknameOrLoginId(final Nickname nickname, final LoginId loginId);
}
