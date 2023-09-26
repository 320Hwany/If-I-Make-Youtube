package youtube.application.subscription.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.ChannelCache;
import youtube.domain.member.persist.Member;
import youtube.domain.subscription.Subscription;
import youtube.global.exception.NotFoundException;
import youtube.mapper.channel.ChannelMapper;
import youtube.mapper.subscription.SubscriptionMapper;
import youtube.util.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SubscriptionsDeleterTest extends ServiceTest {

    @Autowired
    private SubscriptionsDeleter subscriptionsDeleter;

    @Test
    @DisplayName("구독 취소 요청을 한 채널의 정보가 구독 테이블에 없으면 예외가 발생합니다")
    void subscriptionCancelFail() {
        // expected
        assertThatThrownBy(() -> subscriptionsDeleter.command(9999, 9999))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("구독을 취소하면 회원, 채널의 연결 테이블인 구독 테이블의 정보가 삭제되고 구독자 수가 1 감소합니다")
    void subscriptionCancelSuccess() {
        // given
        Member member = saveMember();

        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);

        Subscription subscription = SubscriptionMapper.toEntity(member.getId(), channel.getId());
        subscriptionRepository.save(subscription);

        // when
        subscriptionsDeleter.command(member.getId(), channel.getId());
        ChannelCache channelCache = channelCacheReader.getByChannelId(channel.getId());

        // then
        assertThat(subscriptionRepository.count()).isEqualTo(0);
        assertThat(channelCache.getSubscribersCount()).isEqualTo(-1);
    }
}