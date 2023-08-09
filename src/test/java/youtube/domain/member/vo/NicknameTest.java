package youtube.domain.member.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.global.exception.BadRequestException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NicknameTest {

    @Test
    @DisplayName("닉네임은 한글, 영어, 숫자, 특수문자로 구성된다 (필수 조건은 없음, 공백 가능) - 조건에 맞지 않으면 예외 발생")
    void validateCreationFail() {
        // fail 1 - 닉네임은 6자리 안됨
        assertThatThrownBy(() -> Nickname.from("y"))
                .isInstanceOf(BadRequestException.class);

        // fail 2 - 닉네임은 20자리 넘음
        assertThatThrownBy(() -> Nickname.from("Hello World 123456789"))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("닉네임은 한글, 영어, 숫자로 구성된다 (필수 조건은 없음, 공백 가능)")
    void validateCreationSuccess() {
        // given
        Nickname nickname = Nickname.from("닉네임 Nickname1");

        // expected
        assertThat(nickname).isNotNull();
    }
}