package youtube.domain.member.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import youtube.exception.member.LoginIdLengthException;
import youtube.exception.member.LoginIdRegexException;
import youtube.exception.member.NickNameLengthException;
import youtube.exception.member.NickNameRegexException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class NicknameTest {

    @Test
    @DisplayName("닉네임은 한글, 영어, 숫자로 구성된다 (필수 조건은 없음, 공백 가능) - 조건에 맞지 않으면 예외 발생")
    void validateCreationFail() {
        // fail 1 - 닉네임은 6자리 안됨
        assertThatThrownBy(() -> Nickname.from("y"))
                .isInstanceOf(NickNameLengthException.class);

        // fail 2 - 닉네임은 한글, 영어, 숫자로만 구성해야함 (공백 가능)
        assertThatThrownBy(() -> Nickname.from("닉네임 12 !@"))
                .isInstanceOf(NickNameRegexException.class);

        // fail 1 - 닉네임은 20자리 넘음
        assertThatThrownBy(() -> Nickname.from("Hello World 123456789"))
                .isInstanceOf(NickNameLengthException.class);
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