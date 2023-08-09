package youtube.domain.member.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import youtube.global.exception.BadRequestException;

import java.util.Objects;
import java.util.regex.Pattern;

import static youtube.global.constant.ExceptionMessageConstant.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public final class Password {

    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MAXIMUM_PASSWORD_LENGTH = 16;

    // 비밀번호는 한글, 영어, 숫자와 최소 1개 이상의 특수문자를 사용해야 한다
    private static final String REGEX = "^[가-힣a-zA-Z0-9].*[#?!@$%^&*-]+$";

    @Column(name = "password", nullable = false)
    private String value;

    private Password(final String value) {
        validateCreation(value);
        this.value = value;
    }

    public static Password from(final String value) {
        return new Password(value);
    }

    private void validateCreation(final String password) {
        if (password.length() < MINIMUM_PASSWORD_LENGTH || password.length() > MAXIMUM_PASSWORD_LENGTH) {
            throw new BadRequestException(PASSWORD_LENGTH.message);
        } else if (!Pattern.matches(REGEX, password)) {
            throw new BadRequestException(PASSWORD_REGEX.message);
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
        throw new BadRequestException(PASSWORD_NOT_MATCH.message);
    }

    public void update(final Password password) {
        this.value = password.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
