package youtube.application.video.video_info.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.channel.persist.Channel;
import youtube.domain.member.persist.Member;
import youtube.domain.video.video_info.vo.VideoType;
import youtube.global.exception.NotFoundException;
import youtube.mapper.channel.ChannelMapper;
import youtube.mapper.video.video_info.dto.VideoInfoSaveRequest;
import youtube.util.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VideoInfoSaveTestInfoTest extends ServiceTest {

    @Autowired
    private VideoInfoCreator videoInfoCreator;

    @Test
    @DisplayName("회원의 채널을 찾을 수 없으면 동영상 정보를 저장할 수 없습니다")
    void commandVideoInfoSaveFail() {
        // given
        VideoInfoSaveRequest dto = VideoInfoSaveRequest.builder()
                .videoTitle("동영상 제목")
                .videoType(VideoType.NORMAL)
                .videoDescription("동영상 설명")
                .build();

        // expected
        assertThatThrownBy(() -> videoInfoCreator.command(9999L, dto, ".mp4"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("회원의 채널 정보와 동영상 정보를 받아 DB에 저장합니다")
    void commandVideoInfoSaveSuccess() {
        // given
        Member member = saveMember();

        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);

        VideoInfoSaveRequest dto = VideoInfoSaveRequest.builder()
                .videoTitle("동영상 제목")
                .videoType(VideoType.NORMAL)
                .videoDescription("동영상 설명")
                .build();

        // when
        videoInfoCreator.command(member.getId(), dto, ".mp4");

        // then
        assertThat(videoInfoRepository.count()).isEqualTo(1);
    }
}