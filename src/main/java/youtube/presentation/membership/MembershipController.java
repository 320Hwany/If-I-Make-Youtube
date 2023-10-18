package youtube.presentation.membership;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youtube.application.membership.implement.MembershipCreator;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;

@RequestMapping("/api/v2")
@RestController
public class MembershipController {

    private final MembershipCreator membershipCreator;

    public MembershipController(final MembershipCreator membershipCreator) {
        this.membershipCreator = membershipCreator;
    }

    @PostMapping("/membership/{channelId}")
    public void joinMembership(@Login final MemberSession memberSession,
                               @PathVariable final long channelId) {
        membershipCreator.joinMembership(memberSession.id(), channelId);
    }
}
