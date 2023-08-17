package youtube.application.subscription;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import youtube.application.subscription.command.CommandButtonUpdate;
import youtube.application.subscription.query.QueryChannelFindAll;
import youtube.domain.channel.persist.Channel;

import java.util.List;

import static youtube.global.constant.AnnotationMessageConstant.*;

@Service
public class ButtonSyncService {

    private final QueryChannelFindAll queryChannelFindAll;
    private final CommandButtonUpdate commandButtonUpdate;

    public ButtonSyncService(final QueryChannelFindAll queryChannelFindAll,
                             final CommandButtonUpdate commandButtonUpdate) {
        this.queryChannelFindAll = queryChannelFindAll;
        this.commandButtonUpdate = commandButtonUpdate;
    }

    // 오전 5시에 구독자 수에 따른 유튜브 버튼 정보를 업데이트
    @Async
    @Scheduled(cron = FIVE_AM)
    public void syncButton() {
        List<Channel> channels = queryChannelFindAll.query();
        for (Channel entity : channels) {
            commandButtonUpdate.command(entity);
        }
    }
}
