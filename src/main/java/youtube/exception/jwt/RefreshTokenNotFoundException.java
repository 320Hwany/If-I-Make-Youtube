package youtube.exception.jwt;

import youtube.global.exception.NotFoundException;

import static youtube.global.constant.ExceptionMessageConstant.REFRESH_TOKEN_NOT_FOUND;

public class RefreshTokenNotFoundException extends NotFoundException {

    private static final String MESSAGE = REFRESH_TOKEN_NOT_FOUND.message;

    public RefreshTokenNotFoundException() {
        super(MESSAGE);
    }
}
