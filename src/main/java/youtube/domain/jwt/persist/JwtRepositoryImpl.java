package youtube.domain.jwt.persist;

import org.springframework.stereotype.Repository;
import youtube.global.exception.NotFoundException;

import java.util.Optional;

import static youtube.global.constant.ExceptionMessageConstant.*;

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
                .orElseThrow(() -> new NotFoundException(REFRESH_TOKEN_NOT_EXIST.message));
    }

    @Override
    public Optional<JwtRefreshToken> findByMemberId(long memberId) {
        return jwtJpaRepository.findByMemberId(memberId);
    }
}