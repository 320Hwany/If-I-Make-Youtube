package youtube.global.exception;

import lombok.Getter;
import youtube.global.constant.ExceptionMessageConstant;
import youtube.global.constant.StatusCodeConstant;

import static youtube.global.constant.StatusCodeConstant.NOT_FOUND;
import static youtube.global.constant.StatusCodeConstant.UNAUTHORIZED;

@Getter
public class UnAuthorizedException extends RuntimeException {

    private final String statusCode = UNAUTHORIZED.statusCode;
    private final String message;

    public UnAuthorizedException(final String message) {
        this.message = message;
    }
}
