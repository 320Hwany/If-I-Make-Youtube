package youtube.domain.member.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.global.exception.BadRequestException;

import static org.assertj.core.api.Assertions.*;

class LoginIdTest {

    @Test
    @DisplayName("아이디는 한글, 영어, 숫자로 구성된다 (필수 조건은 없음, 공백 안됨) - 조건에 맞지 않으면 예외 발생")
    void validateCreationFail() {
        // fail 1 - 로그인 아이디 6자리 안됨
        assertThatThrownBy(() -> LoginId.from("아이디12"))
                .isInstanceOf(BadRequestException.class);

        // fail 2 - 로그인 아이디 한글, 영어, 숫자로만 구성해야함
        assertThatThrownBy(() -> LoginId.from("아이디12!@"))
                .isInstanceOf(BadRequestException.class);

        // fail 1 - 로그인 아이디 16자리 넘음
        assertThatThrownBy(() -> LoginId.from("아이디12345678912345"))
                .isInstanceOf(BadRequestException.class);

    }

    @Test
    @DisplayName("아이디는 한글, 영어, 숫자로 구성된다 (필수 조건은 없음, 공백 안됨)")
    void validateCreationSuccess() {
        // given
        LoginId loginId = LoginId.from("로그인아이디id1");

        // expected
        assertThat(loginId).isNotNull();
    }
}