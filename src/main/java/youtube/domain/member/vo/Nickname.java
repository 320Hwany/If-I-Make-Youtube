package youtube.domain.member.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.global.exception.BadRequestException;

import java.util.Objects;
import java.util.regex.Pattern;

import static youtube.global.constant.ExceptionMessageConstant.NICKNAME_LENGTH;
import static youtube.global.constant.ExceptionMessageConstant.NICKNAME_REGEX;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Nickname {

    private static final int MINIMUM_NICKNAME_LENGTH = 2;
    private static final int MAXIMUM_NICKNAME_LENGTH = 20;

    // 닉네임은 한글, 영어, 숫자로 구성된다 (필수 조건은 없음, 공백 가능)
    private static final String REGEX = "^[가-힣a-zA-Z0-9\s]+$";

    @Column(name = "nickname", unique = true, nullable = false)
    private String value;

    private Nickname(final String value) {
        validateCreation(value);
        this.value = value;
    }

    public static Nickname from(final String value) {
        return new Nickname(value);
    }

    private void validateCreation(final String nickname) {
        if (nickname.length() < MINIMUM_NICKNAME_LENGTH || nickname.length() > MAXIMUM_NICKNAME_LENGTH) {
            throw new BadRequestException(NICKNAME_LENGTH.message);
        } else if (!Pattern.matches(REGEX, nickname)) {
            throw new BadRequestException(NICKNAME_REGEX.message);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nickname nickname = (Nickname) o;
        return Objects.equals(value, nickname.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
