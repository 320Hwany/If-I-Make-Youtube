package youtube.repository.jwt;

import youtube.domain.jwt.JwtRefreshToken;

import java.util.Optional;

public interface JwtRepository {

    void save(final JwtRefreshToken jwtRefreshToken);

    JwtRefreshToken getByMemberId(final long memberId);

    Optional<JwtRefreshToken> findByMemberId(final long memberId);

    void deleteByMemberId(final long memberId);

    long count();
}
