package youtube.presentation.subscription;

import org.springframework.web.bind.annotation.*;
import youtube.application.subscription.command.CommandSubscriptionSave;
import youtube.application.subscription.command.CommandSubscriptionCancel;
import youtube.application.subscription.query.QuerySubscriptionsByMemberId;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;
import youtube.mapper.subscription.dto.SubscriptionChannelsCache;

import java.util.List;

@RequestMapping("/api")
@RestController
public class SubscriptionController {

    private final QuerySubscriptionsByMemberId querySubscriptionsByMemberId;
    private final CommandSubscriptionSave commandSubscriptionSave;
    private final CommandSubscriptionCancel commandSubscriptionCancel;

    public SubscriptionController(final QuerySubscriptionsByMemberId querySubscriptionsByMemberId,
                                  final CommandSubscriptionSave commandSubscriptionSave,
                                  final CommandSubscriptionCancel commandSubscriptionCancel) {
        this.querySubscriptionsByMemberId = querySubscriptionsByMemberId;
        this.commandSubscriptionSave = commandSubscriptionSave;
        this.commandSubscriptionCancel = commandSubscriptionCancel;
    }

    @GetMapping("/subscriptions")
    public List<SubscriptionChannelsCache> getSubscriptionChannels(@Login final MemberSession memberSession) {
        return querySubscriptionsByMemberId.query(memberSession.id());
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
