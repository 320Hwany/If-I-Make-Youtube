package youtube.presentation.channel;

import org.springframework.web.bind.annotation.*;
import youtube.application.channel.query.QueryChannelCache;
import youtube.application.channel.command.CommandChannelUpdate;
import youtube.domain.channel.vo.ChannelCache;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;

@RequestMapping("/api")
@RestController
public class ChannelController {

    private final CommandChannelUpdate commandChannelUpdate;
    private final QueryChannelCache queryChannelCache;

    public ChannelController(final CommandChannelUpdate commandChannelUpdate,
                             final QueryChannelCache queryChannelCache) {
        this.commandChannelUpdate = commandChannelUpdate;
        this.queryChannelCache = queryChannelCache;
    }

    @GetMapping("/channelCache/{channelId}")
    public ChannelCache getChannelCache(@PathVariable final long channelId) {
        return queryChannelCache.getCache(channelId);
    }

    @PatchMapping("/channel/channelName")
    public void updateChannelName(@Login final MemberSession memberSession,
                                  @RequestBody final ChannelName channelName) {
        commandChannelUpdate.updateChannelName(memberSession.id(), channelName);
    }

    @PatchMapping("/channel/channelDescription")
    public void updateChannelDescription(@Login final MemberSession memberSession,
                                         @RequestBody final ChannelDescription channelDescription) {
        commandChannelUpdate.updateChannelDescription(memberSession.id(), channelDescription);
    }
}
