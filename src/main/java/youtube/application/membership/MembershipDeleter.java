package youtube.application.membership;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.membership.persist.Membership;
import youtube.global.exception.NotFoundException;
import youtube.repository.membership.MembershipRepository;

import java.util.Optional;

import static youtube.global.constant.ExceptionMessageConstant.MEMBERSHIP_NOT_FOUND;

@Service
public class MembershipDeleter {

    private final MembershipRepository membershipRepository;

    public MembershipDeleter(final MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Transactional
    public void withdraw(final long memberId, final long channelId) {
        Optional<Membership> membershipOptional = membershipRepository
                .findByMemberIdAndChannelId(memberId, channelId);
        Membership membership = getValidMembership(membershipOptional);
        membershipRepository.delete(membership);
    }

    private Membership getValidMembership(final Optional<Membership> membershipOptional) {
        if (membershipOptional.isEmpty()) {
            throw new NotFoundException(MEMBERSHIP_NOT_FOUND.message);
        }

        return membershipOptional.get();
    }
}
