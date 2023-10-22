package youtube.application.jwt;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.jwt.JwtRefreshToken;
import youtube.repository.jwt.JwtRepository;
import youtube.domain.member.vo.MemberSession;
import youtube.mapper.jwt.JwtRefreshTokenMapper;

import java.util.Optional;

@Service
public class TokenManager {

    private final TokenCreator tokenCreator;
    private final HeaderSetter headerSetter;
    private final JwtRepository jwtRepository;

    public TokenManager(final TokenCreator tokenCreator, final HeaderSetter headerSetter,
                        final JwtRepository jwtRepository) {
        this.tokenCreator = tokenCreator;
        this.headerSetter = headerSetter;
        this.jwtRepository = jwtRepository;
    }

    public String createAccessToken(final MemberSession memberSession, final long expired) {
        return tokenCreator.createAccessToken(memberSession, expired);
    }

    public String createRefreshToken(final long memberId, final long expired) {
        return tokenCreator.createRefreshToken(memberId, expired);
    }

    public void setHeader(final HttpServletResponse response, final String accessToken,
                          final String refreshToken) {
        headerSetter.setAccessTokenHeader(response, accessToken);
        headerSetter.setRefreshTokenCookie(response, refreshToken);
    }

    @Transactional
    public void createJwtRefreshToken(final long memberId, final String refreshToken) {
        Optional<JwtRefreshToken> optionalJwtRefreshToken = jwtRepository.findByMemberId(memberId);
        if (optionalJwtRefreshToken.isPresent()) {
            JwtRefreshToken jwtRefreshToken = optionalJwtRefreshToken.get();
            jwtRefreshToken.update(refreshToken);
        } else {
            JwtRefreshToken entity = JwtRefreshTokenMapper.toEntity(memberId, refreshToken);
            jwtRepository.save(entity);
        }
    }
}
