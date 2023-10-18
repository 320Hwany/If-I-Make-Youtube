package youtube.application.subscription.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.application.subscription.implement.SubscriptionsCreator;
import youtube.domain.channel.persist.Channel;
import youtube.domain.member.persist.Member;
import youtube.domain.subscription.Subscription;
import youtube.global.exception.BadRequestException;
import youtube.mapper.channel.ChannelMapper;
import youtube.domain.channel.vo.ChannelCache;
import youtube.mapper.subscription.SubscriptionMapper;
import youtube.util.AcceptanceTest;
import youtube.util.ServiceTest;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

@AcceptanceTest
class SubscriptionsCreatorTest extends ServiceTest {

    @Autowired
    private SubscriptionsCreator subscriptionsCreator;

    @Test
    @DisplayName("이미 구독중인 채널은 여러번 구독할 수 없습니다")
    void subscribeDuplicate() {
        // given
        Member member = saveMember();

        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);

        Subscription subscription = SubscriptionMapper.toEntity(member.getId(), channel.getId());
        subscriptionRepository.save(subscription);

        // expected
        assertThatThrownBy(() -> subscriptionsCreator.command(member.getId(), channel.getId()))
                .isInstanceOf(BadRequestException.class);
    }
    
    @Test
    @DisplayName("회원이 채널을 구독하면 회원과 채널의 연결 엔티티인 Subscription이 생성되고 구독자 수가 1 증가합니다")
    void subscribe() {
        // given
        Member member = saveMember();

        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);

        // when
        subscriptionsCreator.command(member.getId(), channel.getId());
        ChannelCache channelCache = channelCacheReader.getByChannelId(channel.getId());

        // then
        AtomicInteger atomicSubscribersCount = channelCache.getSubscribersCount();
        int subscribersCount = atomicSubscribersCount.get();

        assertThat(subscriptionRepository.count()).isEqualTo(1);
        assertThat(subscribersCount).isEqualTo(1);
    }
}