package youtube.application.member.command;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.member.persist.Member;
import youtube.domain.member.persist.MemberRepository;
import youtube.domain.member.persist.MemberSession;
import youtube.domain.member.vo.Password;
import youtube.mapper.member.MemberMapper;
import youtube.mapper.member.dto.MemberLoginRequest;

@Transactional(readOnly = true)
@Service
public class CommandMemberLogin {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public CommandMemberLogin(final MemberRepository memberRepository,
                              final PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void command(final MemberLoginRequest dto, final HttpServletRequest request) {
        Member entity = memberRepository.getByLoginId(dto.loginId());
        Password password = entity.getPassword();
        if (password.validateMatchPassword(passwordEncoder, dto.password())) {
            MemberSession memberSession = MemberMapper.toMemberSession(entity);
            memberSession.makeSession(request);
        }
    }
}
