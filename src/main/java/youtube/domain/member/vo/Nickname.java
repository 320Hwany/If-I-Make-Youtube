package youtube.domain.member.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import youtube.exception.member.NickNameLengthException;
import youtube.exception.member.NickNameRegexException;

import java.util.regex.Pattern;

@Getter
@Embeddable
public class Nickname {

    private static final int MINIMUM_NICKNAME_LENGTH = 2;
    private static final int MAXIMUM_NICKNAME_LENGTH = 20;
    private static final String REGEX = "^[가-힣a-zA-Z0-9]+$";

    @Column(name = "nickname", unique = true, nullable = false)
    private String value;

    public Nickname(final String nickname) {
        validate(nickname);
        this.value = nickname;
    }

    protected Nickname() {
    }

    private void validate(final String nickname) {
        if (nickname.length() < MINIMUM_NICKNAME_LENGTH || nickname.length() > MAXIMUM_NICKNAME_LENGTH) {
            throw new NickNameLengthException();
        } else if (!Pattern.matches(REGEX, nickname)) {
            throw new NickNameRegexException();
        }
    }
}
