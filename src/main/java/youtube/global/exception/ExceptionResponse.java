package youtube.global.exception;

public record ExceptionResponse(
        String statusCode,
        String message
) {
}
