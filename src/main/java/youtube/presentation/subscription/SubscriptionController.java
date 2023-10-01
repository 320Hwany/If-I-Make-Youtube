package youtube.presentation.subscription;

import org.springframework.web.bind.annotation.*;
import youtube.application.subscription.command.SubscriptionsCreator;
import youtube.application.subscription.command.SubscriptionsDeleter;
import youtube.application.subscription.query.SubscriptionsCacheReader;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;
import youtube.mapper.subscription.SubscriptionMapper;
import youtube.mapper.subscription.dto.SubscriptionChannelsCache;
import youtube.mapper.subscription.dto.SubscriptionResult;

import java.util.List;

@RequestMapping("/api")
@RestController
public class SubscriptionController {

    private final SubscriptionsCacheReader subscriptionsCacheReader;
    private final SubscriptionsCreator subscriptionsCreator;
    private final SubscriptionsDeleter subscriptionsDeleter;

    public SubscriptionController(final SubscriptionsCacheReader subscriptionsCacheReader,
                                  final SubscriptionsCreator subscriptionsCreator,
                                  final SubscriptionsDeleter subscriptionsDeleter) {
        this.subscriptionsCacheReader = subscriptionsCacheReader;
        this.subscriptionsCreator = subscriptionsCreator;
        this.subscriptionsDeleter = subscriptionsDeleter;
    }

    @GetMapping("/v2/subscriptions")
    public SubscriptionResult getSubscriptionChannels(@Login final MemberSession memberSession) {
        List<SubscriptionChannelsCache> caches = subscriptionsCacheReader.findAllByMemberId(memberSession.id());
        return SubscriptionMapper.toResult(caches, caches.size());
    }

    @PostMapping("/v2/subscriptions")
    public void subscribe(@Login final MemberSession memberSession,
                          @RequestParam final long channelId) {
        subscriptionsCreator.command(memberSession.id(), channelId);
    }

    @DeleteMapping("/v2/subscriptions")
    public void cancelSubscription(@Login final MemberSession memberSession,
                                   @RequestParam final long channelId) {
        subscriptionsDeleter.command(memberSession.id(), channelId);
    }
}
