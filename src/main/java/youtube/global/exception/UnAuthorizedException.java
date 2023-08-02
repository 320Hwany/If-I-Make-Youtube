package youtube.global.exception;

import lombok.Getter;
import youtube.global.constant.ExceptionMessageConstant;
import youtube.global.constant.StatusCodeConstant;

@Getter
public class UnAuthorizedException extends RuntimeException {

    private final String statusCode = StatusCodeConstant.UNAUTHORIZED.statusCode;
    private final String message = ExceptionMessageConstant.UNAUTHORIZED.message;
}
