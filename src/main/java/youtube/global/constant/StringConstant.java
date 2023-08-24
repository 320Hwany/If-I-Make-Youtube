package youtube.global.constant;

public enum StringConstant {

    MEMBER_SESSION("MemberSession"),

    ACCESS_TOKEN("AccessToken"),
    REFRESH_TOKEN("RefreshToken"),
    DOT(".");


    public final String value;

    StringConstant(final String value) {
        this.value = value;
    }
}
