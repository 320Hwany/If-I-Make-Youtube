package youtube.application.channel;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.Button;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.mapper.channel.ChannelMapper;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.member.MemberRepository;
import youtube.util.AcceptanceTest;

import static org.assertj.core.api.Assertions.*;
import static youtube.util.TestConstant.*;


@AcceptanceTest
class ButtonSyncServiceTest {

    @Autowired
    private ButtonSyncService buttonSyncService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Test
    @DisplayName("구독자 수가 변경되어도 버튼 등급이 같으면 버튼은 변경되지 않습니다")
    void syncButtonNoEffect() {
        // given
        Member member1 = Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .build();

        memberRepository.save(member1);
        Channel channel = ChannelMapper.toEntity(member1);

        channel.updateSubscribersCount(10000);
        channelRepository.save(channel);

        // when
        buttonSyncService.syncButton();
        Channel psChannel = channelRepository.getById(channel.getId());

        // then
        assertThat(psChannel.getButton()).isEqualTo(Button.NORMAL);
    }

    @Test
    @DisplayName("구독자 수가 변경되면 다음 스케줄러가 작업을 할 때 해당 구독자 수에 맞는 버튼으로 변경합니다")
    void syncButton() {
        // given
        Member member1 = Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .build();

        memberRepository.save(member1);
        Channel channel = ChannelMapper.toEntity(member1);

        channel.updateSubscribersCount(1000000);
        channelRepository.save(channel);

        // when
        buttonSyncService.syncButton();
        Channel psChannel = channelRepository.getById(channel.getId());

        // then
        assertThat(psChannel.getButton()).isEqualTo(Button.GOLD);
    }
}