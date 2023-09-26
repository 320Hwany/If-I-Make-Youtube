package youtube.application.video.video_info.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.domain.video.video_info.vo.VideoInfoCache;
import youtube.global.exception.NotFoundException;
import youtube.util.ServiceTest;

import static org.assertj.core.api.Assertions.*;

class VideoInfoCacheReaderTest extends ServiceTest {

    @Autowired
    private VideoInfoCacheReader videoInfoCacheReader;

    @Test
    @DisplayName("id와 일치하는 동영상 정보가 없다면 동영상 캐시 정보를 가져올 수 없습니다")
    void queryVideoInfoCacheByIdFail() {
        // expected
        assertThatThrownBy(() -> videoInfoCacheReader.query(9999L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("id와 일치하는 동영상 정보가 있다면 동영상 캐시 정보를 가져옵니다")
    void queryVideoInfoCacheByIdSuccess() {
        // given
        VideoInfo videoInfo = VideoInfo.builder()
                .channelId(1L)
                .videoTitle("동영상 제목")
                .build();

        videoInfoRepository.save(videoInfo);

        // when
        VideoInfoCache cache = videoInfoCacheReader.query(videoInfo.getId());

        // then
        assertThat(cache.getVideoTitle()).isEqualTo(videoInfo.getVideoTitle());
    }
}