package youtube.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(final LoginId loginId);

    boolean existsByNicknameOrLoginId(final Nickname nickname, final LoginId loginId);
}
