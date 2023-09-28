package youtube.presentation.member;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import youtube.application.jwt.command.RefreshTokenDeleter;
import youtube.application.member.MemberSignupFacade;
import youtube.application.member.command.PasswordUpdater;
import youtube.application.member.MemberLoginFacade;
import youtube.application.member.query.MemberReader;
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

    private final MemberSignupFacade memberSignupFacade;
    private final MemberLoginFacade memberLoginFacade;
    private final MemberReader memberReader;
    private final RefreshTokenDeleter refreshTokenDeleter;
    private final PasswordUpdater passwordUpdater;

    public MemberController(final MemberSignupFacade memberSignupFacade, final MemberLoginFacade memberLoginFacade,
                            final MemberReader memberReader, final RefreshTokenDeleter refreshTokenDeleter,
                            final PasswordUpdater passwordUpdater) {
        this.memberSignupFacade = memberSignupFacade;
        this.memberLoginFacade = memberLoginFacade;
        this.memberReader = memberReader;
        this.refreshTokenDeleter = refreshTokenDeleter;
        this.passwordUpdater = passwordUpdater;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid final MemberSignupRequest dto) {
        memberSignupFacade.signup(dto);
    }

    @PostMapping("/login")
    public MemberDetailedResponse login(@RequestBody @Valid final MemberLoginRequest dto,
                                        final HttpServletResponse response) {
        Member entity = memberLoginFacade.login(dto, response);
        return MemberMapper.toMemberDetailedResponse(entity);
    }

    @PostMapping("/logout")
    public void logout(@Login final MemberSession memberSession) {
        refreshTokenDeleter.command(memberSession.id());
    }

    // DB 조회 없이 AccessToken 만으로 회원 정보 가져오기
    @GetMapping("/members")
    public MemberResponse getMember(@Login final MemberSession memberSession) {
        return MemberMapper.toMemberResponse(memberSession);
    }

    // 자세한 회원 정보가 필요할 경우 DB에서 회원 정보 가져오기
    @GetMapping("/members/detailed")
    public MemberDetailedResponse getDetailedMember(@Login final MemberSession memberSession) {
        return memberReader.getMemberResponseById(memberSession.id());
    }

    @PatchMapping("/members/password")
    public void updatePassword(@Login final MemberSession memberSession,
                               @RequestBody final Password updatePassword) {
        passwordUpdater.command(memberSession.id(), updatePassword);
    }
}
