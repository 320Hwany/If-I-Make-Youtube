package youtube.presentation.channel;

import org.springframework.web.bind.annotation.*;
import youtube.application.channel.query.QueryChannelCacheById;
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
    private final QueryChannelCacheById queryChannelCacheById;

    public ChannelController(final CommandChannelUpdate commandChannelUpdate,
                             final QueryChannelCacheById queryChannelCacheById) {
        this.commandChannelUpdate = commandChannelUpdate;
        this.queryChannelCacheById = queryChannelCacheById;
    }

    @GetMapping("/channelCache/{channelId}")
    public ChannelCache getChannelCache(@PathVariable final long channelId) {
        return queryChannelCacheById.query(channelId);
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
