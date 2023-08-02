package youtube.global.constant;

// 사용자에게는 보여지지 않고 로그에 사용하는 예외 메세지임
public enum ExceptionMessageForLog {

    HTTP_SERVLET_REQUEST_NOTFOUND("HttpServletRequest이 존재하지 않습니다");

    public final String message;

    ExceptionMessageForLog(final String message) {
        this.message = message;
    }
}
