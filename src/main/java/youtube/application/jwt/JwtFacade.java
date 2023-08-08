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
public class JwtFacade {

    private final CreateTokenService createTokenService;
    private final SetHeaderService setHeaderService;
    private final JwtRepository jwtRepository;

    public JwtFacade(final CreateTokenService createTokenService, final SetHeaderService setHeaderService,
                     final JwtRepository jwtRepository) {
        this.createTokenService = createTokenService;
        this.setHeaderService = setHeaderService;
        this.jwtRepository = jwtRepository;
    }

    public String createAccessToken(final MemberSession memberSession, final long expired) {
        return createTokenService.createAccessToken(memberSession, expired);
    }

    public String createRefreshToken(final long memberId, final long expired) {
        return createTokenService.createRefreshToken(memberId, expired);
    }

    public void setHeader(final HttpServletResponse response, final String accessToken,
                          final String refreshToken) {
        setHeaderService.setAccessTokenHeader(response, accessToken);
        setHeaderService.setRefreshTokenCookie(response, refreshToken);
    }

    @Transactional
    public void saveJwtRefreshToken(final long memberId, final String refreshToken) {
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
