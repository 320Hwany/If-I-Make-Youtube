package youtube.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youtube.application.member.command.CommandMemberSignup;
import youtube.mapper.member.dto.MemberLoginRequest;
import youtube.mapper.member.dto.MemberSignupRequest;

@RequestMapping("/api")
@RestController
public class MemberController {

    private final CommandMemberSignup commandMemberSignup;

    public MemberController(final CommandMemberSignup commandMemberSignup) {
        this.commandMemberSignup = commandMemberSignup;
    }

    @PostMapping("/member")
    public void signup(@RequestBody final MemberSignupRequest dto) {
        commandMemberSignup.command(dto);
    }
}
