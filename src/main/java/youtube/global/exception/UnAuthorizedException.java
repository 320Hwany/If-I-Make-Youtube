package youtube.global.exception;

import lombok.Getter;
import youtube.global.constant.ExceptionMessageConstant;
import youtube.global.constant.StatusCodeConstant;

import static youtube.global.constant.StatusCodeConstant.NOT_FOUND;

@Getter
public class UnAuthorizedException extends RuntimeException {

    private final String statusCode = NOT_FOUND.statusCode;
    private final String message;

    public UnAuthorizedException(final String message) {
        this.message = message;
    }
}
