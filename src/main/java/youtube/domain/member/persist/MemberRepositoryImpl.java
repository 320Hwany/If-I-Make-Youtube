package youtube.domain.member.persist;

import org.springframework.stereotype.Repository;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public MemberRepositoryImpl(final MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public void save(final Member member) {
        memberJpaRepository.save(member);
    }

    @Override
    public boolean existsByNicknameOrLoginId(final Nickname nickname, final LoginId loginId) {
        return memberJpaRepository.existsByNicknameOrLoginId(nickname, loginId);
    }
}
