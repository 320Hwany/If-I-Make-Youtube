package youtube.application.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.Password;
import youtube.repository.member.MemberRepository;

@Service
public class PasswordUpdater {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordUpdater(final MemberRepository memberRepository, final PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void command(final long memberId, final Password updatePassword) {
        Member member = memberRepository.getById(memberId);
        Password password = member.getPassword();
        password.update(updatePassword);
        password.encode(passwordEncoder);
    }
}
