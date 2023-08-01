package youtube.domain.member.exception;

import youtube.global.exception.BadRequestException;

import static youtube.global.constant.ExceptionMessageConstant.*;

public class NickNameRegexException extends BadRequestException {

    private static final String MESSAGE = NICKNAME_REGEX.message;

    public NickNameRegexException() {
        super(MESSAGE);
    }
}
