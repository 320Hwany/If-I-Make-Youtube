package youtube.presentation;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import youtube.application.member.query.QueryMemberLogin;
import youtube.application.member.command.CommandMemberSignup;
import youtube.domain.member.persist.Member;
import youtube.domain.member.persist.MemberSession;
import youtube.global.argument_resolver.Login;
import youtube.mapper.member.MemberMapper;
import youtube.mapper.member.dto.MemberLoginRequest;
import youtube.mapper.member.dto.MemberResponse;
import youtube.mapper.member.dto.MemberSignupRequest;

@RequestMapping("/api")
@RestController
public class MemberController {

    private final CommandMemberSignup commandMemberSignup;
    private final QueryMemberLogin queryMemberLogin;

    public MemberController(final CommandMemberSignup commandMemberSignup,
                            final QueryMemberLogin queryMemberLogin) {
        this.commandMemberSignup = commandMemberSignup;
        this.queryMemberLogin = queryMemberLogin;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid final MemberSignupRequest dto) {
        commandMemberSignup.command(dto);
    }

    @PostMapping("/login")
    public MemberResponse login(@RequestBody @Valid final MemberLoginRequest dto,
                                final HttpServletResponse response) {
        Member entity = queryMemberLogin.query(dto, response);
        return MemberMapper.toMemberResponse(entity);
    }

    @GetMapping("/member")
    public MemberResponse getMember(@Login final MemberSession memberSession) {
        return MemberMapper.toMemberResponse(memberSession);
    }
}
