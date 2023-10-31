package youtube.presentation.membership;

import org.springframework.web.bind.annotation.*;
import youtube.application.membership.MembershipCreator;
import youtube.application.membership.MembershipDeleter;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;

@RequestMapping("/api/v2")
@RestController
public class MembershipController {

    private final MembershipCreator membershipCreator;
    private final MembershipDeleter membershipDeleter;

    public MembershipController(final MembershipCreator membershipCreator,
                                final MembershipDeleter membershipDeleter) {
        this.membershipCreator = membershipCreator;
        this.membershipDeleter = membershipDeleter;
    }

    @PostMapping("/membership/{channelId}")
    public void joinMembership(@Login final MemberSession memberSession,
                               @PathVariable final long channelId) {
        membershipCreator.joinMembership(memberSession.id(), channelId);
    }

    @DeleteMapping("/membership/{channelId}")
    public void withdrawMembership(@Login final MemberSession memberSession,
                                   @PathVariable final long channelId) {
        membershipDeleter.withdraw(memberSession.id(), channelId);
    }
}
