package youtube.presentation.channel;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youtube.application.channel.command.CommandChannelUpdate;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;

@RequestMapping("/api")
@RestController
public class ChannelController {

    private final CommandChannelUpdate commandChannelUpdate;

    public ChannelController(final CommandChannelUpdate commandChannelUpdate) {
        this.commandChannelUpdate = commandChannelUpdate;
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
