package youtube.global.exception;

public record ValidExceptionResponse(
        String statusCode,
        StringBuffer message
) {
}
