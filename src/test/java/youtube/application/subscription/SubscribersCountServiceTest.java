package youtube.application.subscription;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.application.channel.ChannelCacheService;
import youtube.domain.channel.persist.Channel;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.mapper.channel.ChannelMapper;
import youtube.domain.channel.vo.ChannelCache;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.member.MemberRepository;
import youtube.util.AcceptanceTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static youtube.util.TestConstant.*;

@AcceptanceTest
class SubscribersCountServiceTest {

    @Autowired
    private SubscribersCountService subscribersCountService;

    @Autowired
    private ChannelCacheService channelCacheService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Test
    @DisplayName("여러 명의 사용자가 동시에 같은 채널에 구독을 해도 구독자 수가 증가하는 동시성 문제를 처리할 수 있습니다")
    public void increaseSubscribersConcurrent() throws Exception {
        // given 1 - member, channel save
        Member member = Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .build();
        memberRepository.save(member);

        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);

        // given 2 - cache
        ChannelCache channelCache = channelCacheService.getCache(channel.getId());

        // given 3 - thread
        int numThreads = 10;
        int incrementsPerThread = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // when
        for (int i = 0; i < numThreads; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    subscribersCountService.increaseCount(channel.getId(), channelCache);
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