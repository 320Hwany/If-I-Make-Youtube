package youtube.application.video.video_watch_later.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.video.video_watch_later.persist.VideoWatchLater;
import youtube.util.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;


class CommandVideoWatchLaterSaveTest extends ServiceTest {

    @Autowired
    private CommandVideoWatchLaterSave commandVideoWatchLaterSave;

    @Test
    @DisplayName("나중에 볼 동영상 정보가 이미 있으면 DB에 중복 저장하지 않습니다")
    void commandVideoWatchLaterSaveFail() {
        // given
        long memberId = 1L;
        long videoInfoId = 1L;

        videoWatchLaterRepository.save(VideoWatchLater.builder()
                .memberId(memberId)
                .videoInfoId(videoInfoId)
                .build());

        // when
        commandVideoWatchLaterSave.command(memberId, videoInfoId);

        // then
        assertThat(videoWatchLaterRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("나중에 볼 동영상 정보를 DB에 저장합니다")
    void commandVideoWatchLaterSave() {
        // given
        long memberId = 1L;
        long videoInfoId = 1L;

        // when
        commandVideoWatchLaterSave.command(memberId, videoInfoId);

        // then
        assertThat(videoWatchLaterRepository.count()).isEqualTo(1);
    }
}