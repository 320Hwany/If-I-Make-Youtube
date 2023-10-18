package youtube.repository.membership;

import org.springframework.stereotype.Repository;
import youtube.domain.membership.persist.Membership;

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
}
