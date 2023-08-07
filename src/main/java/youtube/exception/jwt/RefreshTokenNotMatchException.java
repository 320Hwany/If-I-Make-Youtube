package youtube.exception.jwt;

import youtube.global.exception.BadRequestException;

import static youtube.global.constant.ExceptionMessageConstant.*;

public class RefreshTokenNotMatchException extends BadRequestException {

    private static final String MESSAGE = REFRESH_TOKEN_NOT_MATCH.message;

    public RefreshTokenNotMatchException() {
        super(MESSAGE);
    }
}
