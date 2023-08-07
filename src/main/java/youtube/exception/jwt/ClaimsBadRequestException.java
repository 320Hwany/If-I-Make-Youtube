package youtube.exception.jwt;

import youtube.global.exception.BadRequestException;

import static youtube.global.constant.ExceptionMessageConstant.CLAIMS_BAD_REQUEST;

public class ClaimsBadRequestException extends BadRequestException {

    private static final String MESSAGE = CLAIMS_BAD_REQUEST.message;

    public ClaimsBadRequestException() {
        super(MESSAGE);
    }
}
