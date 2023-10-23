package youtube.application.video.video_watch_later.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.application.video.video_watch_later.VideoWatchLaterDeleter;
import youtube.domain.video.video_watch_later.persist.VideoWatchLater;
import youtube.global.exception.NotFoundException;
import youtube.util.ServiceTest;

import static org.assertj.core.api.Assertions.*;

class VideoWatchLaterDeleterTest extends ServiceTest {

    @Autowired
    private VideoWatchLaterDeleter videoWatchLaterDeleter;

    @Test
    @DisplayName("나중에 볼 동영상에 대한 정보가 없으면 삭제에 실패합니다")
    void commandVideoWatchLaterDeleteFail() {
        // given
        long memberId = 1L;
        long videoInfoId = 1L;

        // when
        assertThatThrownBy(() -> videoWatchLaterDeleter.command(memberId, videoInfoId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("나중에 볼 동영상 삭제에 성공합니다")
    void commandVideoWatchLaterDeleteSuccess() {
        // given
        long memberId = 1L;
        long videoInfoId = 1L;

        videoWatchLaterRepository.save(VideoWatchLater.builder()
                .memberId(memberId)
                .videoInfoId(videoInfoId)
                .build());

        // when
        videoWatchLaterDeleter.command(memberId, videoInfoId);

        // then
        assertThat(videoWatchLaterRepository.count()).isEqualTo(0);
    }
}