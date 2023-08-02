package youtube.exception.member;

import youtube.global.exception.BadRequestException;

import static youtube.global.constant.ExceptionMessageConstant.*;

public class PasswordNotMatchException extends BadRequestException {

    private static final String MESSAGE = PASSWORD_NOT_MATCH.message;

    public PasswordNotMatchException() {
        super(MESSAGE);
    }
}
