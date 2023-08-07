package youtube.domain.jwt.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtJpaRepository extends JpaRepository<JwtRefreshToken, Long> {

    Optional<JwtRefreshToken> findByMemberId(final long memberId);
}
