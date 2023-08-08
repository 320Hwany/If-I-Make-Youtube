package youtube.util;

public enum TestConstant {

    TEST_NICKNAME("닉네임"),
    TEST_LOGIN_ID("로그인아이디"),
    TEST_PASSWORD("password123!"),
    UPDATE_REFRESH_TOKEN("update refresh token");

    public final String value;

    TestConstant(final String value) {
        this.value = value;
    }
}
