package youtube.global.constant;

public enum ExceptionMessageForLog {

    HTTP_SERVLET_REQUEST_NOTFOUND("HttpServletRequest이 존재하지 않습니다");

    public final String message;

    ExceptionMessageForLog(final String message) {
        this.message = message;
    }
}
