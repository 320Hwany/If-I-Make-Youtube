package youtube.repository.membership;

import youtube.domain.membership.persist.Membership;

public interface MembershipRepository {

    void save(final Membership membership);

    long count();
}
