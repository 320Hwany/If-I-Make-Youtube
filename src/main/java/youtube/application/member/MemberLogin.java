package youtube.application.member;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import youtube.application.jwt.TokenFacade;
import youtube.application.member.query.MemberReader;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.MemberSession;
import youtube.domain.member.vo.Password;
import youtube.mapper.member.MemberMapper;
import youtube.mapper.member.dto.MemberLoginRequest;

import static youtube.global.constant.TimeConstant.*;

@Service
public class MemberLogin {

    private final MemberReader memberReader;
    private final TokenFacade tokenFacade;
    private final PasswordEncoder passwordEncoder;

    public MemberLogin(final MemberReader memberReader, final TokenFacade tokenFacade,
                       final PasswordEncoder passwordEncoder) {
        this.memberReader = memberReader;
        this.tokenFacade = tokenFacade;
        this.passwordEncoder = passwordEncoder;
    }

    public Member login(final MemberLoginRequest dto, final HttpServletResponse response) {
        Member entity = memberReader.query(dto.loginId());
        Password password = entity.getPassword();

        if (password.validateMatchPassword(passwordEncoder, dto.password())) {
            MemberSession memberSession = MemberMapper.toMemberSession(entity);
            String accessToken = tokenFacade.createAccessToken(memberSession, ONE_HOUR.value);
            String refreshToken = tokenFacade.createRefreshToken(memberSession.id(), ONE_MONTH.value);
            tokenFacade.saveJwtRefreshToken(memberSession.id(), refreshToken);
            tokenFacade.setHeader(response, accessToken, refreshToken);
        }

        return entity;
    }
}
