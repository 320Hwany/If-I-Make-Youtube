package youtube.domain.channel.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.global.exception.BadRequestException;

import java.util.Objects;
import java.util.regex.Pattern;

import static youtube.global.constant.ExceptionMessageConstant.LOGIN_ID_LENGTH;
import static youtube.global.constant.ExceptionMessageConstant.LOGIN_ID_REGEX;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public final class ChannelName {

    private static final int MINIMUM_CHANNEL_NAME_LENGTH = 2;
    private static final int MAXIMUM_CHANNEL_NAME_LENGTH = 16;

    // 채널명은 한글, 영어, 숫자, 특수문자로 구성된다 (필수 조건은 없음, 공백 가능)
    private static final String REGEX = "^[가-힣a-zA-Z0-9\\s!@#$%^&*()_+{}\\[\\]:;<>,.?~\\-=/]+$";

    @Column(name = "channelName", unique = true, nullable = false)
    private String value;

    private ChannelName(final String value) {
        validateCreation(value);
        this.value = value;
    }

    public static ChannelName from(final String value) {
        return new ChannelName(value);
    }

    private void validateCreation(final String loginId) {
        if (loginId.length() < MINIMUM_CHANNEL_NAME_LENGTH || loginId.length() > MAXIMUM_CHANNEL_NAME_LENGTH) {
            throw new BadRequestException(LOGIN_ID_LENGTH.message);
        } else if (!Pattern.matches(REGEX, loginId)) {
            throw new BadRequestException(LOGIN_ID_REGEX.message);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelName that = (ChannelName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
