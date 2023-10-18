package youtube.presentation.channel;

import org.springframework.web.bind.annotation.*;
import youtube.application.channel.business.ChannelUpdaterBusiness;
import youtube.application.channel.implement.ChannelCacheReader;
import youtube.domain.channel.vo.ChannelCache;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;

@RequestMapping("/api")
@RestController
public class ChannelController {

    private final ChannelCacheReader channelCacheReader;
    private final ChannelUpdaterBusiness channelUpdaterBusiness;

    public ChannelController(final ChannelCacheReader channelCacheReader,
                             final ChannelUpdaterBusiness channelUpdaterBusiness) {
        this.channelCacheReader = channelCacheReader;
        this.channelUpdaterBusiness = channelUpdaterBusiness;
    }

    @GetMapping("/v1/channel-cache/{channelId}")
    public ChannelCache getChannelCache(@PathVariable final long channelId) {
        return channelCacheReader.getByChannelId(channelId);
    }

    @PatchMapping("/v2/channels/channel-name")
    public void updateChannelName(@Login final MemberSession memberSession,
                                  @RequestBody final ChannelName channelName) {
        channelUpdaterBusiness.updateChannelName(memberSession.id(), channelName);
    }

    @PatchMapping("/v2/channels/channel-description")
    public void updateChannelDescription(@Login final MemberSession memberSession,
                                         @RequestBody final ChannelDescription channelDescription) {
        channelUpdaterBusiness.updateChannelDescription(memberSession.id(), channelDescription);
    }
}
