package youtube.application.channel.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.application.channel.business.ChannelUpdaterBusiness;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.ChannelCache;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;
import youtube.domain.member.persist.Member;
import youtube.mapper.channel.ChannelMapper;
import youtube.util.ServiceTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelUpdaterBusinessTest extends ServiceTest {

    @Autowired
    private ChannelUpdaterBusiness channelUpdaterBusiness;

    @Test
    @DisplayName("로그인한 회원의 채널명을 수정합니다")
    void channelNameUpdate() {
        // given
        Member member = saveMember();

        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);
        ChannelName channelNameUpdate = ChannelName.from("수정 채널명");

        // when
        channelUpdaterBusiness.updateChannelName(member.getId(), channelNameUpdate);
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
        channelUpdaterBusiness.updateChannelDescription(member.getId(), channelDescriptionUpdate);
        Channel psChannel = channelRepository.getByMemberId(member.getId());

        // then
        assertThat(psChannel.getChannelDescription()).isEqualTo(channelDescriptionUpdate);
    }

    @Test
    @DisplayName("로그인한 회원의 채널명/채널 설명과 구독자 수가 동시에 수정되어도 동시성 문제가 발생하지 않습니다")
    void channelNameUpdateWithSubscriberCount() throws Exception {
        // given 1 - member, channel save
        Member member = saveMember();

        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);
        ChannelName channelNameUpdate = ChannelName.from("수정 채널명");
        ChannelDescription channelDescriptionUpdate = ChannelDescription.from("수정 채널 설명");

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
                    channelUpdaterBusiness.updateChannelName(member.getId(), channelNameUpdate);
                    channelCache.increaseSubscribersCount();;
                    channelUpdaterBusiness.updateChannelDescription(member.getId(), channelDescriptionUpdate);
                }
            }, executorService);

            futures.add(future);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.get(); // Wait for all threads to complete

        executorService.shutdown();

        // then
        AtomicInteger atomicSubscribersCount = channelCache.getSubscribersCount();
        int subscribersCount = atomicSubscribersCount.get();
        int expectedCount = incrementsPerThread * numThreads;
        Channel psChannel = channelRepository.getByMemberId(member.getId());

        assertThat(subscribersCount).isEqualTo(expectedCount);
        assertThat(psChannel.getChannelName()).isEqualTo(channelNameUpdate);
        assertThat(psChannel.getChannelDescription()).isEqualTo(channelDescriptionUpdate);
    }
}