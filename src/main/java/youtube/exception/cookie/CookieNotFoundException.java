package youtube.exception.cookie;

import youtube.global.exception.NotFoundException;

import static youtube.global.constant.ExceptionMessageConstant.COOKIE_NOT_FOUND;

public class CookieNotFoundException extends NotFoundException {

    private static final String MESSAGE = COOKIE_NOT_FOUND.message;

    public CookieNotFoundException() {
        super(MESSAGE);
    }
}
