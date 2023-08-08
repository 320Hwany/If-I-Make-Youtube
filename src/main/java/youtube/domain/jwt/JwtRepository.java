package youtube.domain.jwt;

import java.util.Optional;

public interface JwtRepository {

    void save(final JwtRefreshToken jwtRefreshToken);

    JwtRefreshToken getByMemberId(final long memberId);

    Optional<JwtRefreshToken> findByMemberId(final long memberId);

    long count();
}
