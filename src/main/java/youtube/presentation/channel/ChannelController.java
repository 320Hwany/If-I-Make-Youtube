package youtube.presentation.channel;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youtube.application.channel.command.CommandChannelNameUpdate;
import youtube.domain.channel.vo.ChannelName;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;

@RequestMapping("/api")
@RestController
public class ChannelController {

    private final CommandChannelNameUpdate commandChannelNameUpdate;

    public ChannelController(final CommandChannelNameUpdate commandChannelNameUpdate) {
        this.commandChannelNameUpdate = commandChannelNameUpdate;
    }

    @PatchMapping("/channel/channelName")
    public void updateChannelName(@Login final MemberSession memberSession,
                                  @RequestBody final ChannelName channelName) {
        commandChannelNameUpdate.command(memberSession.id(), channelName);
    }
}
