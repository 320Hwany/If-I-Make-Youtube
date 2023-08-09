package youtube.application.channel.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.ChannelName;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.mapper.channel.ChannelMapper;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.member.MemberRepository;
import youtube.util.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static youtube.util.TestConstant.*;

@AcceptanceTest
class CommandChannelNameUpdateTest {

    @Autowired
    private CommandChannelNameUpdate commandChannelNameUpdate;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Test
    @DisplayName("로그인한 회원의 채널명을 수정합니다")
    void channelNameUpdate() {
        // given
        Member member = Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .build();

        memberRepository.save(member);
        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);
        ChannelName channelNameUpdate = ChannelName.from("수정 채널명");

        // when
        commandChannelNameUpdate.command(member.getId(), channelNameUpdate);
        Channel psChannel = channelRepository.getByMemberId(member.getId());

        // then
        assertThat(psChannel.getChannelName()).isEqualTo(channelNameUpdate);
    }
}