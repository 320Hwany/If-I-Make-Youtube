package youtube.application.subscription;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import youtube.domain.channel.persist.Channel;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.mapper.channel.ChannelMapper;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.member.MemberRepository;
import youtube.util.AcceptanceTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static youtube.global.constant.CacheConstant.SUBSCRIBERS_COUNT;
import static youtube.util.TestConstant.*;

@AcceptanceTest
class SubscribeFacadeTest {

    @Autowired
    private SubscribeFacade subscribeFacade;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private CacheManager cacheManager;

//    @Test
//    @DisplayName("여러 명의 사용자가 동시에 같은 채널에 구독을 해도 구독자 수가 증가하는 동시성 문제를 처리할 수 있습니다")
//    public void increaseSubscribersConcurrent() throws Exception {
//        // given 1 - member, channel save
//        Member member = Member.builder()
//                .nickname(Nickname.from(TEST_NICKNAME.value))
//                .loginId(LoginId.from(TEST_LOGIN_ID.value))
//                .password(Password.from(TEST_PASSWORD.value))
//                .build();
//        memberRepository.save(member);
//
//        Channel channel = ChannelMapper.toEntity(member);
//        channelRepository.save(channel);
//
//        // given 2 - cache
//        subscribeFacade.getCache(channel.getId());
//
//        // given 3 - thread
//        int numThreads = 10;
//        int incrementsPerThread = 100;
//
//        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
//        List<CompletableFuture<Void>> futures = new ArrayList<>();
//
//        // when
//        for (int i = 0; i < numThreads; i++) {
//            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//                for (int j = 0; j < incrementsPerThread; j++) {
//                    subscribeFacade.increaseSubscribers(channel.getId());
//                }
//            }, executorService);
//
//            futures.add(future);
//        }
//
//        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
//        allOf.get(); // Wait for all threads to complete
//
//        executorService.shutdown();
//
//        // then
//        Cache cache = cacheManager.getCache(SUBSCRIBERS_COUNT);
//        Cache.ValueWrapper valueWrapper = cache.get(channel.getId());
//        Integer finalCount = (Integer) valueWrapper.get();
//        int expectedCount = incrementsPerThread * numThreads;
//
//        assertThat(finalCount).isEqualTo(expectedCount);
//    }
}