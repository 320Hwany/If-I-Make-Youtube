package youtube.domain.member.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.exception.member.LoginIdLengthException;
import youtube.exception.member.LoginIdRegexException;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class LoginId {

    private static final int MINIMUM_LOGIN_ID_LENGTH = 6;
    private static final int MAXIMUM_LOGIN_ID_LENGTH = 16;

    // 아이디는 한글, 영어, 숫자로 구성된다 (필수 조건은 없음)
    private static final String REGEX = "^[가-힣a-zA-Z0-9]+$";

    @Column(name = "loginId", unique = true, nullable = false)
    private String value;

    private LoginId(final String value) {
        validateCreation(value);
        this.value = value;
    }

    public static LoginId from(final String value) {
        return new LoginId(value);
    }

    private void validateCreation(final String loginId) {
        if (loginId.length() < MINIMUM_LOGIN_ID_LENGTH || loginId.length() > MAXIMUM_LOGIN_ID_LENGTH) {
            throw new LoginIdLengthException();
        } else if (!Pattern.matches(REGEX, loginId)) {
            throw new LoginIdRegexException();
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginId loginId = (LoginId) o;
        return Objects.equals(value, loginId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
