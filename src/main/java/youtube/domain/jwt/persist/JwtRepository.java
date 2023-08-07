package youtube.domain.jwt.persist;

public interface JwtRepository {

    JwtRefreshToken getByMemberId(final long memberId);
}
