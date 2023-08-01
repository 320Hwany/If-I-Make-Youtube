package youtube.domain.member.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import youtube.domain.member.exception.LoginIdLengthException;
import youtube.domain.member.exception.LoginIdRegexException;

import java.util.regex.Pattern;

@Getter
@Embeddable
public class LoginId {

    private static final int MINIMUM_LOGIN_ID_LENGTH = 6;
    private static final int MAXIMUM_LOGIN_ID_LENGTH = 16;
    private static final String REGEX = "^[가-힣a-zA-Z0-9]+$";

    @Column(name = "loginId", unique = true, nullable = false)
    private String value;

    public LoginId(final String value) {
        this.value = value;
    }

    protected LoginId() {
    }

    protected void validate(final String loginId) {
        if (loginId.length() < MINIMUM_LOGIN_ID_LENGTH || loginId.length() > MAXIMUM_LOGIN_ID_LENGTH) {
            throw new LoginIdLengthException();
        } else if (!Pattern.matches(REGEX, loginId)) {
            throw new LoginIdRegexException();
        }
    }
}
