package youtube.domain.member.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import youtube.exception.member.LoginIdLengthException;
import youtube.exception.member.LoginIdRegexException;

import java.util.regex.Pattern;

@Embeddable
public class LoginId {

    private static final int MINIMUM_LOGIN_ID_LENGTH = 6;
    private static final int MAXIMUM_LOGIN_ID_LENGTH = 16;

    // 아이디는 한글, 영어, 숫자로 구성된다 (필수 조건은 없음)
    private static final String REGEX = "^[가-힣a-zA-Z0-9]+$";

    @Column(name = "loginId", unique = true, nullable = false)
    private String value;

    public LoginId(final String value) {
        validate(value);
        this.value = value;
    }

    protected LoginId() {
    }

    private void validate(final String loginId) {
        if (loginId.length() < MINIMUM_LOGIN_ID_LENGTH || loginId.length() > MAXIMUM_LOGIN_ID_LENGTH) {
            throw new LoginIdLengthException();
        } else if (!Pattern.matches(REGEX, loginId)) {
            throw new LoginIdRegexException();
        }
    }
}
