package youtube.mapper.jwt;

import youtube.domain.jwt.JwtRefreshToken;

public enum JwtRefreshTokenMapper {

    JwtRefreshTokenMapper() {
    };

    public static JwtRefreshToken toEntity(final long memberId, final String refreshToken) {
        return JwtRefreshToken.builder()
                .memberId(memberId)
                .refreshToken(refreshToken)
                .build();
    }
}
