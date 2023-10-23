package youtube.application.video.video_watched;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.util.ServiceTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class VideoWatchedUpdaterTest extends ServiceTest {

    @Autowired
    private VideoWatchedUpdater videoWatchedUpdater;

    @Test
    @DisplayName("이미 시청 기록이 있다면 최근 시청한 날짜를 업데이트합니다")
    void commandVideoWatchedUpdate() {
        // given
        long memberId = 1L;
        long videoInfoId = 1L;

        LocalDateTime watchedDateTime1 =
                LocalDateTime.of(2000, 1, 1, 1, 1, 1);
        LocalDateTime watchedDateTime2 =
                LocalDateTime.of(2000, 1, 1, 1, 1, 2);

        // when
        videoWatchedUpdater.command(memberId, videoInfoId, watchedDateTime1);
        videoWatchedUpdater.command(memberId, videoInfoId, watchedDateTime2);

        // then
        assertThat(videoWatchedRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("동영상을 시청하면 시청기록이 저장됩니다")
    void commandVideoWatchedSave() {
        // given
        long memberId = 1L;
        long videoInfoId = 1L;
        LocalDateTime watchedDateTime =
                LocalDateTime.of(2000, 1, 1, 1, 1, 1);

        // when
        videoWatchedUpdater.command(memberId, videoInfoId, watchedDateTime);

        // then
        assertThat(videoWatchedRepository.count()).isEqualTo(1);
    }
}