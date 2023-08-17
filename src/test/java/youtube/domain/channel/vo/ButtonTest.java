package youtube.domain.channel.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ButtonTest {

    @Test
    @DisplayName("각 구독자 수에 맞는 유튜브 버튼을 반환합니다")
    void buttonForSubscribers() {
        // given
        Button normal = Button.from(99999);
        Button silverStart = Button.from(100000);

        Button silverEnd = Button.from(999999);
        Button GoldStart = Button.from(1000000);

        Button GoldEnd = Button.from(9999999);
        Button DiamondStart = Button.from(10000000);

        Button DiamondEnd = Button.from(99999999);
        Button RedDiamondStart = Button.from(100000000);

        // expected
        assertThat(normal).isEqualTo(Button.NORMAL);

        assertThat(silverStart).isEqualTo(Button.SILVER);
        assertThat(silverEnd).isEqualTo(Button.SILVER);

        assertThat(GoldStart).isEqualTo(Button.GOLD);
        assertThat(GoldEnd).isEqualTo(Button.GOLD);

        assertThat(DiamondStart).isEqualTo(Button.DIAMOND);
        assertThat(DiamondEnd).isEqualTo(Button.DIAMOND);

        assertThat(RedDiamondStart).isEqualTo(Button.RED_DIAMOND);
    }
}