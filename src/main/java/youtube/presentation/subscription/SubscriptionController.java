package youtube.presentation.subscription;

import org.springframework.web.bind.annotation.*;
import youtube.application.subscription.command.CommandSubscriptionSave;
import youtube.application.subscription.command.CommandSubscriptionCancel;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;

@RequestMapping("/api")
@RestController
public class SubscriptionController {

    private final CommandSubscriptionSave commandSubscriptionSave;
    private final CommandSubscriptionCancel commandSubscriptionCancel;

    public SubscriptionController(final CommandSubscriptionSave commandSubscriptionSave,
                                  final CommandSubscriptionCancel commandSubscriptionCancel) {
        this.commandSubscriptionSave = commandSubscriptionSave;
        this.commandSubscriptionCancel = commandSubscriptionCancel;
    }

    @PostMapping("/subscription")
    public void subscribe(@Login final MemberSession memberSession,
                          @RequestParam final long channelId) {
        commandSubscriptionSave.command(memberSession.id(), channelId);
    }

    @DeleteMapping("/subscription")
    public void cancelSubscription(@Login final MemberSession memberSession,
                                   @RequestParam final long channelId) {
        commandSubscriptionCancel.command(memberSession.id(), channelId);
    }
}
