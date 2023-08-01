package youtube.domain.member.exception;

import youtube.global.exception.BadRequestException;

import static youtube.global.constant.ExceptionMessageConstant.PASSWORD_LENGTH;

public class PasswordLengthException extends BadRequestException {

    private static final String MESSAGE = PASSWORD_LENGTH.message;

    public PasswordLengthException() {
        super(MESSAGE);
    }
}
