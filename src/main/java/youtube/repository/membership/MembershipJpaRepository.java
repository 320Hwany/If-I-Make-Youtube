package youtube.repository.membership;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.membership.persist.Membership;

public interface MembershipJpaRepository extends JpaRepository<Membership, Long> {
}
