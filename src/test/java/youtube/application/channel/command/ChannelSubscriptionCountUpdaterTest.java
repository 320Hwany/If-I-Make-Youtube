package youtube.application.channel.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.ChannelCache;
import youtube.domain.member.persist.Member;
import youtube.mapper.channel.ChannelMapper;
import youtube.util.ServiceTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelSubscriptionCountUpdaterTest extends ServiceTest {

    @Autowired
    private ChannelSubscriptionCountUpdater channelSubscriptionCountUpdater;

    @Test
    @DisplayName("캐시에 있는 구독자 수를 DB와 동기화합니다")
    void commandChannelSubscribersUpdate() throws ExecutionException, InterruptedException {
        // given
        Member member = saveMember();

        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);

        // when
        CompletableFuture<Void> future = asyncWork(channel);
        future.get();

        // then
        Channel psChannel = channelRepository.getById(channel.getId());
        assertThat(psChannel.getSubscribersCount()).isEqualTo(10);
    }

    private CompletableFuture<Void> asyncWork(final Channel channel) {
        // given
        ChannelCache channelCache = channelCacheReader.query(channel.getId());

        // when
        for (int i = 0; i < 10; i++) {
            channelCache.increaseSubscribersCount();
        }
        return channelSubscriptionCountUpdater.command(channel.getId(), channelCache);
    }
}