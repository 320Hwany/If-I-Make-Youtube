package youtube.global.exception;

import lombok.Getter;

import static youtube.global.constant.StatusCodeConstant.NOT_FOUND;

@Getter
public abstract class NotFoundException extends RuntimeException{

    private final String statusCode = NOT_FOUND.statusCode;
    private final String message;

    public NotFoundException(final String message) {
        this.message = message;
    }
}
