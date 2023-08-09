package youtube.domain.channel.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.global.exception.BadRequestException;

import java.util.Objects;
import java.util.regex.Pattern;

import static youtube.global.constant.ExceptionMessageConstant.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public final class ChannelName {

    private static final int MINIMUM_CHANNEL_NAME_LENGTH = 2;
    private static final int MAXIMUM_CHANNEL_NAME_LENGTH = 16;

    // 채널명은 한글, 영어, 숫자, 특수문자로 구성된다 (필수 조건은 없음, 공백 가능)
    private static final String REGEX = "^[가-힣a-zA-Z0-9\\s!@#$%^&*()_+{}\\[\\]:;<>,.?~\\-=/]+$";

    @Column(unique = true, nullable = false)
    private String channelName;

    private ChannelName(final String channelName) {
        validateCreation(channelName);
        this.channelName = channelName;
    }

    public static ChannelName from(final String channelName) {
        return new ChannelName(channelName);
    }

    private void validateCreation(final String channelName) {
        if (channelName.length() < MINIMUM_CHANNEL_NAME_LENGTH ||
                channelName.length() > MAXIMUM_CHANNEL_NAME_LENGTH) {
            throw new BadRequestException(CHANNEL_NAME_LENGTH.message);
        } else if (!Pattern.matches(REGEX, channelName)) {
            throw new BadRequestException(CHANNEL_NAME_REGEX.message);
        }
    }

    public void update(final ChannelName channelNameUpdate) {
        this.channelName = channelNameUpdate.channelName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelName that = (ChannelName) o;
        return Objects.equals(channelName, that.channelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelName);
    }
}
