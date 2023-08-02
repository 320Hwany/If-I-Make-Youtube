package youtube.domain.member.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.security.crypto.password.PasswordEncoder;
import youtube.exception.member.PasswordLengthException;
import youtube.exception.member.PasswordNotMatchException;
import youtube.exception.member.PasswordRegexException;

import java.util.regex.Pattern;

@Embeddable
public class Password {

    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MAXIMUM_PASSWORD_LENGTH = 16;

    // 비밀번호는 한글, 영어, 숫자와 최소 1개 이상의 특수문자를 사용해야 한다
    private static final String REGEX = "^[가-힣a-zA-Z0-9].*[#?!@$%^&*-]+$";

    @Column(name = "password", nullable = false)
    private String value;

    protected Password() {
    }

    public Password(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String password) {
        if (password.length() < MINIMUM_PASSWORD_LENGTH || password.length() > MAXIMUM_PASSWORD_LENGTH) {
            throw new PasswordLengthException();
        } else if (!Pattern.matches(REGEX, password)) {
            throw new PasswordRegexException();
        }
    }

    public Password encode(final PasswordEncoder passwordEncoder) {
        value = passwordEncoder.encode(value);
        return this;
    }

    public boolean validateMatchPassword(final PasswordEncoder passwordEncoder,
                                         final Password inputPassword) {
        if (passwordEncoder.matches(inputPassword.value, this.value)) {
            return true;
        }
        throw new PasswordNotMatchException();
    }
}
