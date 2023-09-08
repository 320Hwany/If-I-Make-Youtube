package youtube.application.channel.command;

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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static youtube.util.TestConstant.*;

@AcceptanceTest
class CommandButtonUpdateTest {

    @Autowired
    private CommandButtonUpdate commandButtonUpdate;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Test
    @DisplayName("구독자 수 변화가 현재 유튜브 버튼의 정보와 같으면 업데이트를 진행하지 않습니다")
    void commandButtonUpdateNoEffect() throws ExecutionException, InterruptedException {
        // given
        Member member = Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .build();

        memberRepository.save(member);
        Channel channel = ChannelMapper.toEntity(member);
        channel.updateSubscribersCount(1000);
        channelRepository.save(channel);

        // when
        CompletableFuture<Void> future = asyncWork(channel);
        future.get();

        // then
        Channel psChannel = channelRepository.getById(channel.getId());
        assertThat(psChannel.getButton()).isEqualTo(Button.NORMAL);
    }

    @Test
    @DisplayName("구독자 수 변화가 현재 유튜브 버튼의 정보와 다르면 유튜브 버튼을 업데이트합니다")
    void commandButtonUpdateSuccess() throws InterruptedException, ExecutionException {
        // given
        Member member = Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .build();

        memberRepository.save(member);
        Channel channel = ChannelMapper.toEntity(member);
        channel.updateSubscribersCount(1000000);
        channelRepository.save(channel);

        // when
        CompletableFuture<Void> future = asyncWork(channel);
        future.get();

        // then
        Channel psChannel = channelRepository.getById(channel.getId());
        assertThat(psChannel.getButton()).isEqualTo(Button.GOLD);
    }

    private CompletableFuture<Void> asyncWork(final Channel channel) {
        return commandButtonUpdate.command(channel.getId());
    }
}