package youtube.repository.membership;

import youtube.domain.membership.persist.Membership;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository {

    void save(final Membership membership);

    Membership getById(final long membershipId);

    Optional<Membership> findByMemberIdAndChannelId(final long memberId, final long channelId);

    List<Membership> findAll();

    void delete(final Membership membership);

    long count();
}
