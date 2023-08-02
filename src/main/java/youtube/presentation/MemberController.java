package youtube.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youtube.application.member.command.CommandMemberLogin;
import youtube.application.member.command.CommandMemberSignup;
import youtube.mapper.member.dto.MemberLoginRequest;
import youtube.mapper.member.dto.MemberSignupRequest;

@RequestMapping("/api")
@RestController
public class MemberController {

    private final CommandMemberSignup commandMemberSignup;
    private final CommandMemberLogin commandMemberLogin;

    public MemberController(final CommandMemberSignup commandMemberSignup,
                            final CommandMemberLogin commandMemberLogin) {
        this.commandMemberSignup = commandMemberSignup;
        this.commandMemberLogin = commandMemberLogin;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid final MemberSignupRequest dto) {
        commandMemberSignup.command(dto);
    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid final MemberLoginRequest dto,
                      final HttpServletRequest request) {
        commandMemberLogin.command(dto, request);
    }
}
