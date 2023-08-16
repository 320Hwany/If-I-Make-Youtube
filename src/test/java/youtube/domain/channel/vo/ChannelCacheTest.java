package youtube.domain.channel.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelCacheTest {

    @Test
    @DisplayName("구독을 누르면 캐시 정보로 관리하는 ChannelCache의 구독자 수가 1 증가합니다")
    void increaseSubscribersCount() {
        // given
        ChannelCache channelCache = ChannelCache.builder()
                .subscribersCount(10)
                .build();

        // when
        channelCache.increaseSubscribersCount();

        // then
        assertThat(channelCache.getSubscribersCount()).isEqualTo(11);
    }

    @Test
    @DisplayName("구독을 취소하면 캐시 정보로 관리하는 ChannelCache의 구독자 수가 1 감소합니다")
    void decreaseSubscribersCount() {
        // given
        ChannelCache channelCache = ChannelCache.builder()
                .subscribersCount(10)
                .build();

        // when
        channelCache.decreaseSubscribersCount();

        // then
        assertThat(channelCache.getSubscribersCount()).isEqualTo(9);
    }
}