package youtube.global.constant;

public enum ExceptionMessageConstant {

    NICKNAME_LENGTH("2글자 이상 20글자 이하의 닉네임을 작성해주세요"),
    NICKNAME_REGEX("한글, 영어, 숫자, 특수문자로 이루어진 닉네임을 작성해주세요"),
    CHANNEL_NAME_LENGTH("2글자 이상 20글자 이하의 채널명을 작성해주세요"),
    CHANNEL_NAME_REGEX("한글, 영어, 숫자, 특수문자로 이루어진 채널명을 작성해주세요"),
    LOGIN_ID_LENGTH("6글자 이상 16글자 이하의 아이디를 작성해주세요"),
    LOGIN_ID_REGEX("한글, 영어, 숫자로 이루어진 아이디를 작성해주세요"),
    PASSWORD_LENGTH("6글자 이상 16글자 이하의 비밀번호를 작성해주세요"),
    PASSWORD_REGEX("비밀번호는 한글, 영어, 숫자와 최소 1개 이상의 특수문자를 사용해야 합니다"),
    MEMBER_DUPLICATION("이미 가입된 닉네임/아이디가 있습니다"),
    SUBSCRIBE_DUPLICATION("이미 구독중인 채널입니다"),


    UNAUTHORIZED("로그인 후 이용해주세요"),
    LOGIN_ID_NOTFOUND("아이디와 일치하는 회원이 존재하지 않습니다"),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다"),
    MEMBER_NOT_FOUND("회원을 찾을 수 없습니다"),
    CHANNEL_NOT_FOUND("채널을 찾을 수 없습니다"),
    SUBSCRIPTION_NOT_FOUND("구독 정보를 찾을 수 없습니다"),
    VIDEO_INFO_NOT_FOUND("동영상 정보를 찾을 수 없습니다"),
    VIDEO_NOT_FOUND("동영상을 찾을 수 없습니다"),
    VIDEO_REACTION_NOT_FOUND("동영상에 대한 리액션 정보를 찾을 수 없습니다"),
    VIDEO_WATCH_LATER_NOT_FOUND("나중에 볼 동영상 정보를 찾을 수 없습니다"),
    MEMBERSHIP_NOT_FOUND("멤버십 정보를 찾을 수 없습니다"),
    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다"),
    VIDEO_INFO_SCHEDULER_BAD_REQUEST("동영상 정보를 캐시와 DB를 동기화하는 스케줄러 작업에 실패하였습니다"),
    SUBSCRIBERS_SCHEDULER_BAD_REQUEST("구독자 수를 캐시와 DB를 동기화하는 스케줄러 작업에 실패하였습니다"),
    BUTTON_SCHEDULER_BAD_REQUEST("구독자 수 변화에 따른 유튜브 버튼 업데이트 스케줄러 작업에 실패하였습니다"),
    MEMBERSHIP_SCHEDULER_BAD_REQUEST("가입 기간에 따른 멤버십 단계 업데이트 스케줄러 작업에 실패하였습니다"),

    CLASS_CAST_BAD_REQUEST("클래스 자료 형변환에 실패합니다"),
    SAVE_TO_VIDEO_BAD_REQUEST("동영상을 서버에 저장할 수 없습니다"),
    MALFORMED_URL_BAD_REQUEST("잘못된 형식의 URL을 생성할 수 없습니다"),
    EMPTY_VIDEO_BAD_REQUEST("동영상이 존재하지 않아 서버에 저장할 수 없습니다"),
    VIDEO_UPLOAD_SECURITY_BAD_REQUEST("동영상 저장 디렉토리가 일치하지 않으면 저장할 수 없습니다"),
    MEDIA_TYPE_BAD_REQUEST("지원하지 않는 MediaType입니다"),

    REFRESH_TOKEN_NOT_MATCH("Refresh Token이 일치하지 않습니다"),
    REFRESH_TOKEN_NOT_EXIST("Refresh Token을 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_VALID("Refresh Token이 유효하지 않습니다"),
    ACCESS_TOKEN_JSON_PARSING("Access Token으로부터 정보를 가져올 수 없습니다"),
    MEMBER_SESSION_JSON_PARSING("MemberSession을 Json 형식으로 바꿀 수 없습니다"),
    COOKIE_NOT_EXIST("쿠키가 존재하지 않습니다"),
    CLAIMS_UNAUTHORIZED("토큰으로부터 Claims 정보를 가져올 수 없습니다");

    public final String message;

    ExceptionMessageConstant(final String message) {
        this.message = message;
    }
}
