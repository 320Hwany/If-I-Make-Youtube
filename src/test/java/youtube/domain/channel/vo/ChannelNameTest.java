package youtube.domain.channel.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.domain.member.vo.Nickname;
import youtube.global.exception.BadRequestException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChannelNameTest {

    @Test
    @DisplayName("채널명은 한글, 영어, 숫자, 특수문자로 구성된다 (필수 조건은 없음, 공백 가능) - 조건에 맞지 않으면 예외 발생")
    void validateCreationFail() {
        // fail 1 - 채널명은 6자리 안됨
        assertThatThrownBy(() -> ChannelName.from("y"))
                .isInstanceOf(BadRequestException.class);

        // fail 2 - 채널명은 20자리 넘음
        assertThatThrownBy(() -> ChannelName.from("Hello World 123456789"))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("채널명은 한글, 영어, 숫자, 특수문자로 구성된다 (필수 조건은 없음, 공백 가능)")
    void validateCreationSuccess() {
        // given
        Nickname nickname = Nickname.from("닉네임 Nickname1");

        // expected
        assertThat(nickname).isNotNull();
    }

    @Test
    @DisplayName("채널명을 수정합니다")
    void update() {
        // given
        ChannelName channelName = ChannelName.from("채널명");

        // when
        channelName.update(ChannelName.from("채널명 수정"));

        // then
        assertThat(channelName.getValue()).isEqualTo("채널명 수정");
    }
}