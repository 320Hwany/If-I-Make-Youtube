package youtube.application.channel.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;
import youtube.domain.member.persist.Member;
import youtube.mapper.channel.ChannelMapper;
import youtube.util.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelUpdaterFacadeTest extends ServiceTest {

    @Autowired
    private ChannelUpdater channelUpdater;

    @Test
    @DisplayName("로그인한 회원의 채널명을 수정합니다")
    void channelNameUpdate() {
        // given
        Member member = saveMember();

        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);
        ChannelName channelNameUpdate = ChannelName.from("수정 채널명");

        // when
        channelUpdater.updateChannelName(member.getId(), channelNameUpdate);
        Channel psChannel = channelRepository.getByMemberId(member.getId());

        // then
        assertThat(psChannel.getChannelName()).isEqualTo(channelNameUpdate);
    }

    @Test
    @DisplayName("로그인한 회원의 채널 설명을 수정합니다")
    void channelDescriptionUpdate() {
        // given
        Member member = saveMember();

        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);
        ChannelDescription channelDescriptionUpdate = ChannelDescription.from("수정 채널 설명");

        // when
        channelUpdater.updateChannelDescription(member.getId(), channelDescriptionUpdate);
        Channel psChannel = channelRepository.getByMemberId(member.getId());

        // then
        assertThat(psChannel.getChannelDescription()).isEqualTo(channelDescriptionUpdate);
    }
}