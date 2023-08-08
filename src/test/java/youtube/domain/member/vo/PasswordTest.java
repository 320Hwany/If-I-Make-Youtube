package youtube.domain.member.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import youtube.domain.member.persist.Member;
import youtube.global.exception.BadRequestException;
import youtube.util.TestConstant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static youtube.util.TestConstant.*;

class PasswordTest {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("비밀번호는 한글, 영어, 숫자와 최소 1개 이상의 특수문자를 사용해야 한다 - 조건에 맞지 않으면 예외 발생")
    void validateCreationFail() {
        // fail 1 - 비밀번호 6자리 안됨
        assertThatThrownBy(() -> Password.from("비밀번호!"))
                .isInstanceOf(BadRequestException.class);

        // fail 2 - 특수문자 없음
        assertThatThrownBy(() -> Password.from("비밀번호123"))
                .isInstanceOf(BadRequestException.class);

        // fail 3 - 비밀번호 16자리 넘음
        assertThatThrownBy(() -> Password.from("비밀번호가 17자리 예외발생함!"))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("비밀번호는 한글, 영어, 숫자와 최소 1개 이상의 특수문자를 사용해야 한다")
    void validateCreationSuccess() {
        // success test
        Password password = Password.from("비밀번호123!");
        assertThat(password).isNotNull();
    }

    @Test
    @DisplayName("현재 비밀번호를 주입 받은 PasswordEncoder로 암호화합니다")
    void encode() {
        // given
        Password password = Password.from(TEST_PASSWORD.value);

        // when
        Password encode = password.encode(passwordEncoder);
        boolean matches = passwordEncoder.matches(TEST_PASSWORD.value, encode.getValue());

        // then
        assertThat(matches).isTrue();
    }

    @Test
    @DisplayName("암호화된 비밀번호의 값이 일치하지 않으면 예외가 발생합니다")
    void validateMatchPasswordFail() {
        // given
        Password password = Password.from(TEST_PASSWORD.value);
        password.encode(passwordEncoder);

        // when
        assertThatThrownBy(() -> password.validateMatchPassword(
                passwordEncoder, Password.from("일치하지 않은 비밀번호!"))
        )
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("암호화된 비밀번호의 값이 일치하면 메소드를 통과합니다")
    void validateMatchPasswordSuccess() {
        // given
        Password password = Password.from(TEST_PASSWORD.value);
        password.encode(passwordEncoder);

        // when
        password.validateMatchPassword(passwordEncoder, Password.from(TEST_PASSWORD.value));
    }

    @Test
    @DisplayName("비밀번호를 수정합니다")
    void update() {
        // given
        Password password = Password.from(TEST_PASSWORD.value);

        // when
        password.update(Password.from("수정 비밀번호!"));

        // then
        assertThat(password.getValue()).isEqualTo("수정 비밀번호!");
    }
}