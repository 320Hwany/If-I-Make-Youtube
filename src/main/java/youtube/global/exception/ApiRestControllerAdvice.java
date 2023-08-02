package youtube.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiRestControllerAdvice {

    // 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ExceptionResponse handleException(final BadRequestException e) {
        return new ExceptionResponse(e.getStatusCode(), e.getMessage());
    }

    // 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExceptionForLog.class)
    public ExceptionResponse handleException(final ExceptionForLog e) {
        return new ExceptionResponse(e.getStatusCode(), e.getMessage());
    }

    // 401
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadRequestException.class)
    public ExceptionResponse handleException(final UnAuthorizedException e) {
        return new ExceptionResponse(e.getStatusCode(), e.getMessage());
    }

    // 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ExceptionResponse handleException(final NotFoundException e) {
        return new ExceptionResponse(e.getStatusCode(), e.getMessage());
    }
}
