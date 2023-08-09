package youtube.domain.channel.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelDescriptionTest {

    @Test
    @DisplayName("채널 설명을 수정합니다")
    void update() {
        // given
        ChannelDescription channelDescription = ChannelDescription.from("채널 설명");

        // when
        channelDescription.update(ChannelDescription.from("채널 설명 수정"));

        // then
        assertThat(channelDescription.getValue()).isEqualTo("채널 설명 수정");
    }
}