package youtube.application.video.video_watched;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.application.video.video_watched.command.CommandVideoWatchedSave;
import youtube.util.ServiceTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CommandVideoWatchedSaveTest extends ServiceTest {

    @Autowired
    private CommandVideoWatchedSave commandVideoWatchedSave;

    @Test
    @DisplayName("나중에 볼 동영상을 저장합니다")
    void commandVideoWatchedSave() {
        // given
        long memberId = 1L;
        long videoInfoId = 1L;
        LocalDateTime watchedDateTime =
                LocalDateTime.of(2000, 1, 1, 1, 1, 1);

        // when
        commandVideoWatchedSave.command(memberId, videoInfoId, watchedDateTime);

        // then
        assertThat(videoWatchedRepository.count()).isEqualTo(1);
    }
}