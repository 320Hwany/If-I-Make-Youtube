package youtube.application.subscription.command;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.subscription.Subscription;
import youtube.repository.subscription.SubscriptionRepository;
import youtube.util.AcceptanceTest;

import static org.assertj.core.api.Assertions.*;


@AcceptanceTest
class CommandSubscribeTest {

    @Autowired
    private CommandSubscribe commandSubscribe;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    @DisplayName("회원이 채널을 구독하면 회원과 채널의 연결 엔티티인 Subscription이 생성됩니다")
    void subscribe() {
        // given
        long memberId = 1L;
        long channelId = 2L;

        // when
        commandSubscribe.command(memberId, channelId);
        Subscription entity = subscriptionRepository.getById(1L);

        // then
        assertThat(subscriptionRepository.count()).isEqualTo(1);
        assertThat(entity.getMemberId()).isEqualTo(1L);
        assertThat(entity.getChannelId()).isEqualTo(2L);
    }
}