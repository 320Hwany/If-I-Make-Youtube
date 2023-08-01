package youtube.global.exception;

import lombok.Getter;

import static youtube.global.constant.StatusCodeConstant.*;

@Getter
public abstract class BadRequestException extends RuntimeException {

    private static final String statusCode = BAD_REQUEST.statusCode;
    private final String message;

    public BadRequestException(final String message) {
        this.message = message;
    }
}
