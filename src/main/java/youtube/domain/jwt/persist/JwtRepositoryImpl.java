package youtube.domain.jwt.persist;

import org.springframework.stereotype.Repository;

@Repository
public class JwtRepositoryImpl implements JwtRepository {

    private final JwtJpaRepository jwtJpaRepository;

    public JwtRepositoryImpl(final JwtJpaRepository jwtJpaRepository) {
        this.jwtJpaRepository = jwtJpaRepository;
    }
}
