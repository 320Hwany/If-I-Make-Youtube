package youtube.repository.membership;

import org.springframework.stereotype.Repository;
import youtube.domain.membership.persist.Membership;
import youtube.global.constant.ExceptionMessageConstant;
import youtube.global.exception.NotFoundException;

import java.util.List;

import static youtube.global.constant.ExceptionMessageConstant.*;

@Repository
public class MembershipRepositoryImpl implements MembershipRepository {

    private final MembershipJpaRepository membershipJpaRepository;

    public MembershipRepositoryImpl(final MembershipJpaRepository membershipJpaRepository) {
        this.membershipJpaRepository = membershipJpaRepository;
    }

    @Override
    public void save(final Membership membership) {
        membershipJpaRepository.save(membership);
    }

    @Override
    public Membership getById(final long membershipId) {
        return membershipJpaRepository.findById(membershipId)
                .orElseThrow(() -> new NotFoundException(MEMBERSHIP_NOT_FOUND.message));
    }

    @Override
    public List<Membership> findAll() {
        return membershipJpaRepository.findAll();
    }

    @Override
    public long count() {
        return membershipJpaRepository.count();
    }
}
