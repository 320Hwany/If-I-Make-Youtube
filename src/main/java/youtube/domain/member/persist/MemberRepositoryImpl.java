package youtube.domain.member.persist;

import org.springframework.stereotype.Repository;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.global.exception.NotFoundException;

import static youtube.global.constant.ExceptionMessageConstant.LOGIN_ID_NOTFOUND;
import static youtube.global.constant.ExceptionMessageConstant.MEMBER_NOT_FOUND;

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
                .orElseThrow(() -> new NotFoundException(LOGIN_ID_NOTFOUND.message));
    }

    @Override
    public Member getById(final long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND.message));
    }

    @Override
    public boolean existsByNicknameOrLoginId(final Nickname nickname, final LoginId loginId) {
        return memberJpaRepository.existsByNicknameOrLoginId(nickname, loginId);
    }

    @Override
    public long count() {
        return memberJpaRepository.count();
    }
}
