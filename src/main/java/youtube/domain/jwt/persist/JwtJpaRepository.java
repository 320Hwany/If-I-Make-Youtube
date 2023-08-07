package youtube.domain.jwt.persist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtJpaRepository extends JpaRepository<JwtRefreshToken, Long> {
}
