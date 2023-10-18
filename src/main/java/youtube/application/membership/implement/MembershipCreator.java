package youtube.application.membership.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.membership.persist.Membership;
import youtube.mapper.membership.MembershipMapper;
import youtube.repository.membership.MembershipRepository;

@Service
public class MembershipCreator {

    private final MembershipRepository membershipRepository;

    public MembershipCreator(final MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Transactional
    public void joinMembership(final long memberId, final long channelId) {
        Membership membership = MembershipMapper.toEntity(memberId, channelId);
        membershipRepository.save(membership);
    }
}
