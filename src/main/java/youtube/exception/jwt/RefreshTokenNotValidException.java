package youtube.exception.jwt;

import youtube.global.exception.BadRequestException;

import static youtube.global.constant.ExceptionMessageConstant.REFRESH_TOKEN_NOT_MATCH;

public class RefreshTokenNotValidException extends BadRequestException {

    private static final String MESSAGE = REFRESH_TOKEN_NOT_MATCH.message;

    public RefreshTokenNotValidException() {
        super(MESSAGE);
    }
}
