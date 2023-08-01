package youtube.domain.member.exception;

import youtube.global.exception.BadRequestException;

import static youtube.global.constant.ExceptionMessageConstant.PASSWORD_REGEX;

public class PasswordRegexException extends BadRequestException {

    private static final String MESSAGE = PASSWORD_REGEX.message;

    public PasswordRegexException() {
        super(MESSAGE);
    }
}
