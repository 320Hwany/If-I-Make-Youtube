package youtube.repository.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.jwt.JwtRefreshToken;

import java.util.Optional;

public interface JwtJpaRepository extends JpaRepository<JwtRefreshToken, Long> {

    Optional<JwtRefreshToken> findByMemberId(final long memberId);
}
