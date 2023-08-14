package youtube.application.member.query;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.application.jwt.JwtFacade;
import youtube.domain.jwt.JwtRefreshToken;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.MemberSession;
import youtube.domain.member.vo.Password;
import youtube.mapper.jwt.JwtRefreshTokenMapper;
import youtube.mapper.member.MemberMapper;
import youtube.mapper.member.dto.MemberLoginRequest;

import java.util.Optional;

import static youtube.global.constant.TimeConstant.*;

@Service
public class QueryMemberLogin {

    private final QueryMemberByLoginId queryMemberByLoginId;
    private final JwtFacade jwtFacade;
    private final PasswordEncoder passwordEncoder;

    public QueryMemberLogin(final QueryMemberByLoginId queryMemberByLoginId, final JwtFacade jwtFacade,
                            final PasswordEncoder passwordEncoder) {
        this.queryMemberByLoginId = queryMemberByLoginId;
        this.jwtFacade = jwtFacade;
        this.passwordEncoder = passwordEncoder;
    }

    public Member query(final MemberLoginRequest dto, final HttpServletResponse response) {
        Member entity = queryMemberByLoginId.getByLoginId(dto.loginId());
        Password password = entity.getPassword();

        if (password.validateMatchPassword(passwordEncoder, dto.password())) {
            MemberSession memberSession = MemberMapper.toMemberSession(entity);
            String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);
            String refreshToken = jwtFacade.createRefreshToken(memberSession.id(), ONE_MONTH.value);
            jwtFacade.saveJwtRefreshToken(memberSession.id(), refreshToken);
            jwtFacade.setHeader(response, accessToken, refreshToken);
        }

        return entity;
    }
}
