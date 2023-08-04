package youtube.application.member.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import youtube.domain.member.persist.MemberRepository;
import youtube.domain.member.vo.Nickname;
import youtube.mapper.member.dto.MemberSignupRequest;
import youtube.util.AcceptanceTest;

import static org.junit.jupiter.api.Assertions.*;

@AcceptanceTest
class CommandMemberSignupTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("")
    void test() {
        // given

        // when

        // then
    }
}