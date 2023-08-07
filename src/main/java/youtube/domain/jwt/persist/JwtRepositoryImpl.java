package youtube.domain.jwt.persist;

import org.springframework.stereotype.Repository;
import youtube.exception.jwt.RefreshTokenNotMatchException;

import java.util.Optional;

@Repository
public class JwtRepositoryImpl implements JwtRepository {

    private final JwtJpaRepository jwtJpaRepository;

    public JwtRepositoryImpl(final JwtJpaRepository jwtJpaRepository) {
        this.jwtJpaRepository = jwtJpaRepository;
    }

    @Override
    public void save(final JwtRefreshToken jwtRefreshToken) {
        jwtJpaRepository.save(jwtRefreshToken);
    }

    @Override
    public JwtRefreshToken getByMemberId(final long memberId) {
        return jwtJpaRepository.findByMemberId(memberId)
                .orElseThrow(RefreshTokenNotMatchException::new);
    }

    @Override
    public Optional<JwtRefreshToken> findByMemberId(long memberId) {
        return jwtJpaRepository.findByMemberId(memberId);
    }
}
