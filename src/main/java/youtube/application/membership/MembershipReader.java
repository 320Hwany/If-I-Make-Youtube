package youtube.application.membership;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.membership.persist.Membership;
import youtube.repository.membership.MembershipRepository;

import java.util.List;

@Service
public class MembershipReader {

    private final MembershipRepository membershipRepository;

    public MembershipReader(final MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Transactional(readOnly = true)
    public List<Membership> findAll() {
        return membershipRepository.findAll();
    }
}
