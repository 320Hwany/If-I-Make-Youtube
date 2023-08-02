package youtube.global.exception.dto;

public record ValidExceptionResponse(
        String statusCode,
        StringBuffer message
) {
}
