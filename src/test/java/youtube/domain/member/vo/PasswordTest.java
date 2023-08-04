package youtube.domain.member.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import youtube.exception.member.PasswordLengthException;
import youtube.exception.member.PasswordNotMatchException;
import youtube.exception.member.PasswordRegexException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PasswordTest {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("비밀번호는 한글, 영어, 숫자와 최소 1개 이상의 특수문자를 사용해야 한다 - 조건에 맞지 않으면 예외 발생")
    void validateCreationFail() {
        // fail 1 - 글자수 6자리 안됨
        assertThatThrownBy(() -> Password.from("비밀번호!"))
                .isInstanceOf(PasswordLengthException.class);

        // fail 2 - 특수문자 없음
        assertThatThrownBy(() -> Password.from("비밀번호123"))
                .isInstanceOf(PasswordRegexException.class);

        // fail 3 - 글자수 16자리 넘음
        assertThatThrownBy(() -> Password.from("비밀번호가 17자리 예외발생함!"))
                .isInstanceOf(PasswordLengthException.class);
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
        Password password = Password.from("비밀번호123!");

        // when
        Password encode = password.encode(passwordEncoder);
        boolean matches = passwordEncoder.matches("비밀번호123!", encode.getValue());

        // then
        assertThat(matches).isTrue();
    }

    @Test
    @DisplayName("암호화된 비밀번호의 값이 일치하지 않으면 예외가 발생합니다")
    void validateMatchPasswordFail() {
        // given
        Password password = Password.from("비밀번호123!");
        password.encode(passwordEncoder);

        // when
        assertThatThrownBy(() -> password.validateMatchPassword(
                passwordEncoder, Password.from("일치하지 않은 비밀번호!"))
        )
                .isInstanceOf(PasswordNotMatchException.class);
    }

    @Test
    @DisplayName("암호화된 비밀번호의 값이 일치하면 메소드를 통과합니다")
    void validateMatchPasswordSuccess() {
        // given
        Password password = Password.from("비밀번호123!");
        password.encode(passwordEncoder);

        // when
        password.validateMatchPassword(passwordEncoder, Password.from("비밀번호123!"));
    }
}