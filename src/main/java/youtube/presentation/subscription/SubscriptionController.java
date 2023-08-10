package youtube.presentation.subscription;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import youtube.application.subscription.command.CommandSubscribe;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;

@RequestMapping("/api")
@RestController
public class SubscriptionController {

    private final CommandSubscribe commandSubscribe;

    public SubscriptionController(final CommandSubscribe commandSubscribe) {
        this.commandSubscribe = commandSubscribe;
    }

    @PostMapping("/subscription")
    public void subscribe(@Login final MemberSession memberSession,
                          @RequestParam final long channelId) {
        commandSubscribe.command(memberSession.id(), channelId);
    }
}
