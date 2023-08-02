package youtube.exception.member;

import youtube.global.exception.BadRequestException;

import static youtube.global.constant.ExceptionMessageConstant.LOGIN_ID_NOTFOUND;

public class LoginIdNotFoundException extends BadRequestException {

    private static final String MESSAGE = LOGIN_ID_NOTFOUND.message;

    public LoginIdNotFoundException() {
        super(MESSAGE);
    }
}
