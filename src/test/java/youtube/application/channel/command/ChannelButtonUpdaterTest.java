package youtube.application.channel.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.application.channel.ChannelButtonUpdater;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.Button;
import youtube.domain.member.persist.Member;
import youtube.mapper.channel.ChannelMapper;
import youtube.util.ServiceTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelButtonUpdaterTest extends ServiceTest {

    @Autowired
    private ChannelButtonUpdater channelButtonUpdater;

    @Test
    @DisplayName("구독자 수 변화가 현재 유튜브 버튼의 정보와 같으면 업데이트를 진행하지 않습니다")
    void commandButtonUpdateNoEffect() throws ExecutionException, InterruptedException {
        // given
        Member member = saveMember();

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
        Member member = saveMember();

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
        return channelButtonUpdater.command(channel.getId());
    }
}