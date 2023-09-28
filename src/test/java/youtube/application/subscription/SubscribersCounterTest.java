package youtube.application.subscription;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.channel.persist.Channel;
import youtube.domain.member.persist.Member;
import youtube.mapper.channel.ChannelMapper;
import youtube.domain.channel.vo.ChannelCache;
import youtube.util.ServiceTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

class SubscribersCounterTest extends ServiceTest {

    @Autowired
    private SubscribersCounter subscribersCounter;

    @Test
    @DisplayName("여러 명의 사용자가 동시에 같은 채널에 구독을 해도 구독자 수가 증가하는 동시성 문제를 처리할 수 있습니다")
    public void increaseSubscribersConcurrent() throws Exception {
        // given 1 - member, channel save
        Member member = saveMember();

        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);

        // given 2 - cache
        ChannelCache channelCache = channelCacheReader.getByChannelId(channel.getId());

        // given 3 - thread
        int numThreads = 10;
        int incrementsPerThread = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // when
        for (int i = 0; i < numThreads; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    subscribersCounter.increaseCount(channel.getId(), channelCache);
                }
            }, executorService);

            futures.add(future);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.get(); // Wait for all threads to complete

        executorService.shutdown();

        // then
        int expectedCount = incrementsPerThread * numThreads;
        assertThat(channelCache.getSubscribersCount()).isEqualTo(expectedCount);
    }
}