package youtube.domain.jwt.persist;

import java.util.Optional;

public interface JwtRepository {

    void save(final JwtRefreshToken jwtRefreshToken);

    JwtRefreshToken getByMemberId(final long memberId);

    Optional<JwtRefreshToken> findByMemberId(final long memberId);
}
