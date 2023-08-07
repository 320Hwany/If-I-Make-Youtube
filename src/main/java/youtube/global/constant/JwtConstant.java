package youtube.global.constant;

public enum JwtConstant {

    MEMBER_SESSION("MemberSession"),
    ACCESS_TOKEN("AccessToken"),
    REFRESH_TOKEN("RefreshToken");


    public final String value;

    JwtConstant(final String value) {
        this.value = value;
    }
}
