package youtube.application.member;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import youtube.application.jwt.JwtFacade;
import youtube.application.member.query.QueryMemberByLoginId;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.MemberSession;
import youtube.domain.member.vo.Password;
import youtube.mapper.member.MemberMapper;
import youtube.mapper.member.dto.MemberLoginRequest;

import static youtube.global.constant.TimeConstant.*;

@Service
public class MemberLoginService {

    private final QueryMemberByLoginId queryMemberByLoginId;
    private final JwtFacade jwtFacade;
    private final PasswordEncoder passwordEncoder;

    public MemberLoginService(final QueryMemberByLoginId queryMemberByLoginId, final JwtFacade jwtFacade,
                              final PasswordEncoder passwordEncoder) {
        this.queryMemberByLoginId = queryMemberByLoginId;
        this.jwtFacade = jwtFacade;
        this.passwordEncoder = passwordEncoder;
    }

    public Member login(final MemberLoginRequest dto, final HttpServletResponse response) {
        Member entity = queryMemberByLoginId.query(dto.loginId());
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
