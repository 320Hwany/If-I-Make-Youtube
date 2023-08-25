package youtube.application.video_info.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.channel.persist.Channel;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.domain.video_info.vo.VideoType;
import youtube.global.exception.NotFoundException;
import youtube.mapper.channel.ChannelMapper;
import youtube.mapper.video.dto.VideoSaveRequest;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.member.MemberRepository;
import youtube.repository.video_info.VideoInfoRepository;
import youtube.util.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static youtube.util.TestConstant.*;

@AcceptanceTest
class CommandVideoInfoSaveTestInfo {

    @Autowired
    private CommandVideoInfoSave commandVideoInfoSave;

    @Autowired
    private VideoInfoRepository videoInfoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Test
    @DisplayName("회원의 채널을 찾을 수 없으면 동영상 정보를 저장할 수 없습니다")
    void commandVideoInfoSaveFail() {
        // given
        VideoSaveRequest dto = VideoSaveRequest.builder()
                .videoTitle("동영상 제목")
                .videoType(VideoType.NORMAL)
                .videoDescription("동영상 설명")
                .build();

        // expected
        assertThatThrownBy(() -> commandVideoInfoSave.command(9999L, dto))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("회원의 채널 정보와 동영상 정보를 받아 DB에 저장합니다")
    void commandVideoInfoSaveSuccess() {
        // given
        Member member = Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .build();
        memberRepository.save(member);

        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);

        VideoSaveRequest dto = VideoSaveRequest.builder()
                .videoTitle("동영상 제목")
                .videoType(VideoType.NORMAL)
                .videoDescription("동영상 설명")
                .build();

        // when
        commandVideoInfoSave.command(member.getId(), dto);

        // then
        assertThat(videoInfoRepository.count()).isEqualTo(1);
    }
}