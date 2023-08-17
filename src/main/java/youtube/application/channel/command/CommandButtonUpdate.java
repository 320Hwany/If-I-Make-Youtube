package youtube.application.channel.command;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.Button;


@Service
public class CommandButtonUpdate {

    @Async
    @Transactional
    public void command(final Channel entity) {
        Button button = Button.from(entity.getSubscribersCount());
        entity.updateButton(button);
    }
}
