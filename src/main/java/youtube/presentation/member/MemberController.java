package youtube.presentation.member;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import youtube.application.jwt.command.RefreshTokenDeleter;
import youtube.application.member.command.MemberSignup;
import youtube.application.member.command.PasswordUpdater;
import youtube.application.member.query.MemberDetailedResponseReader;
import youtube.application.member.MemberLogin;
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

    private final MemberSignup memberSignup;
    private final MemberLogin memberLogin;
    private final MemberDetailedResponseReader memberDetailedResponseReader;
    private final RefreshTokenDeleter refreshTokenDeleter;
    private final PasswordUpdater passwordUpdater;

    public MemberController(final MemberSignup memberSignup,
                            final MemberLogin memberLogin,
                            final MemberDetailedResponseReader memberDetailedResponseReader,
                            final RefreshTokenDeleter refreshTokenDeleter,
                            final PasswordUpdater passwordUpdater) {
        this.memberSignup = memberSignup;
        this.memberLogin = memberLogin;
        this.memberDetailedResponseReader = memberDetailedResponseReader;
        this.refreshTokenDeleter = refreshTokenDeleter;
        this.passwordUpdater = passwordUpdater;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid final MemberSignupRequest dto) {
        memberSignup.signup(dto);
    }

    @PostMapping("/login")
    public MemberDetailedResponse login(@RequestBody @Valid final MemberLoginRequest dto,
                                        final HttpServletResponse response) {
        Member entity = memberLogin.login(dto, response);
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
        return memberDetailedResponseReader.query(memberSession.id());
    }

    @PatchMapping("/members/password")
    public void updatePassword(@Login final MemberSession memberSession,
                               @RequestBody final Password updatePassword) {
        passwordUpdater.command(memberSession.id(), updatePassword);
    }
}
