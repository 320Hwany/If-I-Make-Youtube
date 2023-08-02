package youtube.global.exception;

import lombok.Getter;

import static youtube.global.constant.StatusCodeConstant.*;

@Getter
public class ExceptionForLog extends RuntimeException {

    private final String statusCode = BAD_REQUEST.statusCode;
    private final String message;

    public ExceptionForLog(final String message) {
        this.message = message;
    }
}
