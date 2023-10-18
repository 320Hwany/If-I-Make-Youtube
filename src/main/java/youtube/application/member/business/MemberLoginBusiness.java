package youtube.application.member.business;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import youtube.application.jwt.business.TokenBusiness;
import youtube.application.member.implement.MemberReader;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.MemberSession;
import youtube.domain.member.vo.Password;
import youtube.mapper.member.MemberMapper;
import youtube.mapper.member.dto.MemberLoginRequest;

import static youtube.global.constant.TimeConstant.*;

@Service
public class MemberLoginBusiness {

    private final MemberReader memberReader;
    private final TokenBusiness tokenBusiness;
    private final PasswordEncoder passwordEncoder;

    public MemberLoginBusiness(final MemberReader memberReader, final TokenBusiness tokenBusiness,
                               final PasswordEncoder passwordEncoder) {
        this.memberReader = memberReader;
        this.tokenBusiness = tokenBusiness;
        this.passwordEncoder = passwordEncoder;
    }

    public Member login(final MemberLoginRequest dto, final HttpServletResponse response) {
        Member entity = memberReader.getByLoginId(dto.loginId());
        Password password = entity.getPassword();

        if (password.validateMatchPassword(passwordEncoder, dto.password())) {
            MemberSession memberSession = MemberMapper.toMemberSession(entity);
            String accessToken = tokenBusiness.createAccessToken(memberSession, ONE_HOUR.value);
            String refreshToken = tokenBusiness.createRefreshToken(memberSession.id(), ONE_MONTH.value);
            tokenBusiness.createJwtRefreshToken(memberSession.id(), refreshToken);
            tokenBusiness.setHeader(response, accessToken, refreshToken);
        }

        return entity;
    }
}
