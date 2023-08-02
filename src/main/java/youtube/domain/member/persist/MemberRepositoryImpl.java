package youtube.domain.member.persist;

import org.springframework.stereotype.Repository;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.exception.member.LoginIdNotFoundException;

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
    public Member getByLoginId(final LoginId loginId) {
        return memberJpaRepository.findByLoginId(loginId)
                .orElseThrow(LoginIdNotFoundException::new);
    }

    @Override
    public boolean existsByNicknameOrLoginId(final Nickname nickname, final LoginId loginId) {
        return memberJpaRepository.existsByNicknameOrLoginId(nickname, loginId);
    }
}
