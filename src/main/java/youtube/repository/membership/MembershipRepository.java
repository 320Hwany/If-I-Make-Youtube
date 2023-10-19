package youtube.repository.membership;

import youtube.domain.membership.persist.Membership;

import java.util.List;

public interface MembershipRepository {

    void save(final Membership membership);

    Membership getById(final long membershipId);

    List<Membership> findAll();

    long count();
}
