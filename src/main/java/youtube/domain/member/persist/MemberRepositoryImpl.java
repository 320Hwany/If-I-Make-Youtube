package youtube.domain.member.persist;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.global.exception.NotFoundException;
import youtube.mapper.member.dto.MemberDetailedResponse;
import youtube.mapper.member.dto.QMemberDetailedResponse;

import static youtube.domain.member.persist.QMember.*;
import static youtube.global.constant.ExceptionMessageConstant.LOGIN_ID_NOTFOUND;
import static youtube.global.constant.ExceptionMessageConstant.MEMBER_NOT_FOUND;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(final MemberJpaRepository memberJpaRepository, final JPAQueryFactory queryFactory) {
        this.memberJpaRepository = memberJpaRepository;
        this.queryFactory = queryFactory;
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
    public MemberDetailedResponse getDetailedResponseById(final long memberId) {
        return queryFactory.select(new QMemberDetailedResponse(
                        Expressions.asNumber(memberId),
                        member.nickname.value,
                        member.loginId.value,
                        member.roleType,
                        member.gender,
                        member.birthDate,
                        member.likedVideosCount,
                        member.watchLaterVideosCount
                )).from(member)
                .where(member.id.eq(memberId))
                .fetchOne();
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
