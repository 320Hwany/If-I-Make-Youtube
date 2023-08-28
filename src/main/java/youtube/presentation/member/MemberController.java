package youtube.presentation.member;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import youtube.application.jwt.command.CommandJwtDelete;
import youtube.application.member.command.CommandPasswordUpdate;
import youtube.application.member.query.QueryMemberDetailedResponse;
import youtube.application.member.MemberLoginService;
import youtube.application.member.MemberSignupService;
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

    private final MemberSignupService memberSignupService;
    private final MemberLoginService memberLoginService;
    private final QueryMemberDetailedResponse queryMemberDetailedResponse;
    private final CommandJwtDelete commandJwtDelete;
    private final CommandPasswordUpdate commandPasswordUpdate;

    public MemberController(final MemberSignupService memberSignupService,
                            final MemberLoginService memberLoginService,
                            final QueryMemberDetailedResponse queryMemberDetailedResponse,
                            final CommandJwtDelete commandJwtDelete,
                            final CommandPasswordUpdate commandPasswordUpdate) {
        this.memberSignupService = memberSignupService;
        this.memberLoginService = memberLoginService;
        this.queryMemberDetailedResponse = queryMemberDetailedResponse;
        this.commandJwtDelete = commandJwtDelete;
        this.commandPasswordUpdate = commandPasswordUpdate;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid final MemberSignupRequest dto) {
        memberSignupService.signup(dto);
    }

    @PostMapping("/login")
    public MemberDetailedResponse login(@RequestBody @Valid final MemberLoginRequest dto,
                                        final HttpServletResponse response) {
        Member entity = memberLoginService.login(dto, response);
        return MemberMapper.toMemberDetailedResponse(entity);
    }

    @PostMapping("/logout")
    public void logout(@Login final MemberSession memberSession) {
        commandJwtDelete.command(memberSession.id());
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
