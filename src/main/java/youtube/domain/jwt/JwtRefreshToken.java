package youtube.domain.jwt;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import youtube.domain.BaseTimeEntity;
import youtube.global.annotation.Association;

@Getter
@Entity
public class JwtRefreshToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jwt_refresh_token_id")
    private Long id;

    @Association
    private Long memberId;

    private String refreshToken;

    protected JwtRefreshToken() {
    }

    @Builder
    private JwtRefreshToken(final long memberId, final String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }

    public void update(final String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
