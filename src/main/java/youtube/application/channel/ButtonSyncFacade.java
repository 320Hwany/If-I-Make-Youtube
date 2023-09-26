package youtube.application.channel;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import youtube.application.channel.command.ChannelButtonUpdater;
import youtube.application.channel.query.ChannelReader;
import youtube.domain.channel.persist.Channel;
import youtube.global.exception.BadRequestException;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static youtube.global.constant.AnnotationMessageConstant.*;
import static youtube.global.constant.ExceptionMessageConstant.BUTTON_SCHEDULER_BAD_REQUEST;

@Service
public class ButtonSyncFacade {

    private final ChannelReader channelReader;
    private final ChannelButtonUpdater channelButtonUpdater;

    public ButtonSyncFacade(final ChannelReader channelReader,
                            final ChannelButtonUpdater channelButtonUpdater) {
        this.channelReader = channelReader;
        this.channelButtonUpdater = channelButtonUpdater;
    }

    // 오전 5시에 구독자 수에 따른 유튜브 버튼 정보를 업데이트
    @Async
    @Scheduled(cron = FIVE_AM)
    public void syncButton() {
        List<Channel> channels = channelReader.findAll();

        List<CompletableFuture<Void>> futures = channels.stream()
                .map(
                        entity -> CompletableFuture.runAsync(
                                () -> channelButtonUpdater.command(entity.getId())
                        )
                )
                .toList();

        CompletableFuture<Void> result = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        validateResult(result);
    }

    private void validateResult(final CompletableFuture<Void> result) {
        try {
            result.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new BadRequestException(BUTTON_SCHEDULER_BAD_REQUEST.message);
        }
    }
}
