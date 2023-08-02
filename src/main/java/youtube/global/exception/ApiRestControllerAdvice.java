package youtube.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static youtube.global.constant.StatusCodeConstant.*;

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

    // 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidExceptionResponse handleException(final MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        StringBuffer buffer = new StringBuffer();
        for (FieldError fieldError : fieldErrors) {
            buffer.append(" ");
            buffer.append(fieldError.getDefaultMessage());
        }
        return new ValidExceptionResponse(BAD_REQUEST.statusCode, buffer);
    }

    // 401
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
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
