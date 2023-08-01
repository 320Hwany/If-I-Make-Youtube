package youtube.global.constant;

public enum ExceptionMessageConstant {

    NICKNAME_LENGTH("2글자 이상 20글자 이하의 닉네임을 작성해주세요"),
    NICKNAME_REGEX("한글, 영어, 숫자로 이루어진 닉네임을 작성해주세요"),
    LOGIN_ID_LENGTH("2글자 이상 20글자 이하의 아이디를 작성해주세요"),
    LOGIN_ID_REGEX("한글, 영어, 숫자로 이루어진 아이디를 작성해주세요");

    public final String message;

    ExceptionMessageConstant(final String message) {
        this.message = message;
    }
}
