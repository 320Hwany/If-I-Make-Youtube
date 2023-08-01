package youtube.exception.member;

import youtube.global.exception.BadRequestException;

import static youtube.global.constant.ExceptionMessageConstant.LOGIN_ID_LENGTH;

public class LoginIdLengthException extends BadRequestException {

    private static final String MESSAGE = LOGIN_ID_LENGTH.message;

    public LoginIdLengthException() {
        super(MESSAGE);
    }
}
