package youtube.presentation.subscription;

import org.springframework.web.bind.annotation.*;
import youtube.application.subscription.command.CommandSubscriptionSave;
import youtube.application.subscription.command.CommandSubscriptionCancel;
import youtube.application.subscription.query.QuerySubscriptionsByMemberId;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;
import youtube.mapper.subscription.SubscriptionMapper;
import youtube.mapper.subscription.dto.SubscriptionChannelsCache;
import youtube.mapper.subscription.dto.SubscriptionResult;

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
    public SubscriptionResult getSubscriptionChannels(@Login final MemberSession memberSession) {
        List<SubscriptionChannelsCache> caches = querySubscriptionsByMemberId.query(memberSession.id());
        return SubscriptionMapper.toResult(caches, caches.size());
    }

    @PostMapping("/subscriptions")
    public void subscribe(@Login final MemberSession memberSession,
                          @RequestParam final long channelId) {
        commandSubscriptionSave.command(memberSession.id(), channelId);
    }

    @DeleteMapping("/subscriptions")
    public void cancelSubscription(@Login final MemberSession memberSession,
                                   @RequestParam final long channelId) {
        commandSubscriptionCancel.command(memberSession.id(), channelId);
    }
}
