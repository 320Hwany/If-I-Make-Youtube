package youtube.domain.member.exception;

import youtube.global.exception.BadRequestException;

import static youtube.global.constant.ExceptionMessageConstant.*;

public class LoginIdRegexException extends BadRequestException {

    private static final String MESSAGE = LOGIN_ID_REGEX.message;

    public LoginIdRegexException() {
        super(MESSAGE);
    }
}
