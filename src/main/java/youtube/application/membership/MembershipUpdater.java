package youtube.application.membership;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.membership.persist.Membership;
import youtube.repository.membership.MembershipRepository;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
public class MembershipUpdater {

    private final MembershipRepository membershipRepository;

    public MembershipUpdater(final MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Async
    @Transactional
    public CompletableFuture<Void> update(final long membershipId) {
        Membership membership = membershipRepository.getById(membershipId);
        LocalDateTime now = LocalDateTime.now();
        membership.updateLevel(now);
        return CompletableFuture.completedFuture(null);
    }
}
