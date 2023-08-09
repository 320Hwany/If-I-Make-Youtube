package youtube.presentation.member;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import youtube.application.member.command.CommandMemberLogout;
import youtube.application.member.command.CommandPasswordUpdate;
import youtube.application.member.query.QueryMemberDetailedResponse;
import youtube.application.member.query.QueryMemberLogin;
import youtube.application.member.command.CommandMemberSignup;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.MemberSession;
import youtube.domain.member.vo.Password;
import youtube.global.annotation.Login;
import youtube.mapper.member.MemberMapper;
import youtube.mapper.member.dto.MemberLoginRequest;
import youtube.mapper.member.dto.MemberDetailedResponse;
import youtube.mapper.member.dto.MemberResponse;
import youtube.mapper.member.dto.MemberSignupRequest;

@RequestMapping("/api")
@RestController
public class MemberController {

    private final CommandMemberSignup commandMemberSignup;
    private final QueryMemberLogin queryMemberLogin;
    private final QueryMemberDetailedResponse queryMemberDetailedResponse;
    private final CommandMemberLogout commandMemberLogout;
    private final CommandPasswordUpdate commandPasswordUpdate;

    public MemberController(final CommandMemberSignup commandMemberSignup,
                            final QueryMemberLogin queryMemberLogin,
                            final QueryMemberDetailedResponse queryMemberDetailedResponse,
                            final CommandMemberLogout commandMemberLogout,
                            final CommandPasswordUpdate commandPasswordUpdate) {
        this.commandMemberSignup = commandMemberSignup;
        this.queryMemberLogin = queryMemberLogin;
        this.queryMemberDetailedResponse = queryMemberDetailedResponse;
        this.commandMemberLogout = commandMemberLogout;
        this.commandPasswordUpdate = commandPasswordUpdate;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid final MemberSignupRequest dto) {
        commandMemberSignup.command(dto);
    }

    @PostMapping("/login")
    public MemberDetailedResponse login(@RequestBody @Valid final MemberLoginRequest dto,
                                        final HttpServletResponse response) {
        Member entity = queryMemberLogin.query(dto, response);
        return MemberMapper.toMemberDetailedResponse(entity);
    }

    @PostMapping("/logout")
    public void logout(@Login final MemberSession memberSession) {
        commandMemberLogout.command(memberSession.id());
    }

    // DB 조회 없이 AccessToken 만으로 회원 정보 가져오기
    @GetMapping("/member")
    public MemberResponse getMember(@Login final MemberSession memberSession) {
        return MemberMapper.toMemberResponse(memberSession);
    }

    // 자세한 회원 정보가 필요할 경우 DB에서 회원 정보 가져오기
    @GetMapping("/member/detailed")
    public MemberDetailedResponse getDetailedMember(@Login final MemberSession memberSession) {
        return queryMemberDetailedResponse.query(memberSession.id());
    }

    @PatchMapping("/member/password")
    public void updatePassword(@Login final MemberSession memberSession,
                               @RequestBody final Password updatePassword) {
        commandPasswordUpdate.command(memberSession.id(), updatePassword);
    }
}
