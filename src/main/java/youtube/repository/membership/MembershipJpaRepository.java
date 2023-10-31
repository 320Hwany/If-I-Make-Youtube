package youtube.repository.membership;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.membership.persist.Membership;

import java.util.Optional;

public interface MembershipJpaRepository extends JpaRepository<Membership, Long> {

    Optional<Membership> findByMemberIdAndChannelId(final long memberId, final long channelId);
}
