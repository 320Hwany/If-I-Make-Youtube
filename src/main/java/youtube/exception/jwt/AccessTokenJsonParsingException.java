package youtube.exception.jwt;

import youtube.global.exception.BadRequestException;

import static youtube.global.constant.ExceptionMessageConstant.ACCESS_TOKEN_JSON_PARSING;

public class AccessTokenJsonParsingException extends BadRequestException {

    private static final String MESSAGE = ACCESS_TOKEN_JSON_PARSING.message;

    public AccessTokenJsonParsingException() {
        super(MESSAGE);
    }
}
