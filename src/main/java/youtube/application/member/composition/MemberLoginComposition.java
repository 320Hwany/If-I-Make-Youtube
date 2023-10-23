package youtube.application.member.composition;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import youtube.application.jwt.TokenManager;
import youtube.application.member.MemberReader;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.MemberSession;
import youtube.domain.member.vo.Password;
import youtube.mapper.member.MemberMapper;
import youtube.mapper.member.dto.MemberLoginRequest;

import static youtube.global.constant.TimeConstant.*;

@Service
public class MemberLoginComposition {

    private final MemberReader memberReader;
    private final TokenManager tokenManager;
    private final PasswordEncoder passwordEncoder;

    public MemberLoginComposition(final MemberReader memberReader, final TokenManager tokenManager,
                                  final PasswordEncoder passwordEncoder) {
        this.memberReader = memberReader;
        this.tokenManager = tokenManager;
        this.passwordEncoder = passwordEncoder;
    }

    public Member login(final MemberLoginRequest dto, final HttpServletResponse response) {
        Member entity = memberReader.getByLoginId(dto.loginId());
        Password password = entity.getPassword();

        if (password.validateMatchPassword(passwordEncoder, dto.password())) {
            MemberSession memberSession = MemberMapper.toMemberSession(entity);
            String accessToken = tokenManager.createAccessToken(memberSession, ONE_HOUR.value);
            String refreshToken = tokenManager.createRefreshToken(memberSession.id(), ONE_MONTH.value);
            tokenManager.createJwtRefreshToken(memberSession.id(), refreshToken);
            tokenManager.setHeader(response, accessToken, refreshToken);
        }

        return entity;
    }
}
