package youtube.presentation.channel;

import org.springframework.web.bind.annotation.*;
import youtube.application.channel.ChannelUpdateService;
import youtube.application.channel.query.QueryChannelCacheById;
import youtube.domain.channel.vo.ChannelCache;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;

@RequestMapping("/api")
@RestController
public class ChannelController {

    private final QueryChannelCacheById queryChannelCacheById;
    private final ChannelUpdateService channelUpdateService;

    public ChannelController(final QueryChannelCacheById queryChannelCacheById,
                             final ChannelUpdateService channelUpdateService) {
        this.queryChannelCacheById = queryChannelCacheById;
        this.channelUpdateService = channelUpdateService;
    }

    @GetMapping("/channelCache/{channelId}")
    public ChannelCache getChannelCache(@PathVariable final long channelId) {
        return queryChannelCacheById.query(channelId);
    }

    @PatchMapping("/channel/channelName")
    public void updateChannelName(@Login final MemberSession memberSession,
                                  @RequestBody final ChannelName channelName) {
        channelUpdateService.updateChannelName(memberSession.id(), channelName);
    }

    @PatchMapping("/channel/channelDescription")
    public void updateChannelDescription(@Login final MemberSession memberSession,
                                         @RequestBody final ChannelDescription channelDescription) {
        channelUpdateService.updateChannelDescription(memberSession.id(), channelDescription);
    }
}
