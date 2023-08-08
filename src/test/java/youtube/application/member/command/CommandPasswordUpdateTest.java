package youtube.application.member.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.global.exception.NotFoundException;
import youtube.repository.member.MemberRepository;
import youtube.util.AcceptanceTest;

import static org.assertj.core.api.Assertions.*;
import static youtube.util.TestConstant.*;

@AcceptanceTest
class CommandPasswordUpdateTest {

    @Autowired
    private CommandPasswordUpdate commandPasswordUpdate;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인한 회원의 정보가 DB에 존재하지 않으면 비밀번호 변경에 실패합니다")
    void passwordUpdateFail() {
        // given
        long memberId = 1L;
        Password password = Password.from(TEST_PASSWORD.value);

        // expected
        assertThatThrownBy(() -> commandPasswordUpdate.command(memberId, password))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("로그인한 회원의 비밀번호를 수정합니다")
    void passwordUpdateSuccess() {
        // given
        Member entity = Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .build();

        memberRepository.save(entity);
        Password updatePassword = Password.from("수정 비밀번호!");

        // when
        commandPasswordUpdate.command(entity.getId(), updatePassword);

        // then
        Member psEntity = memberRepository.getById(entity.getId());
        Password password = psEntity.getPassword();
        assertThat(passwordEncoder.matches(updatePassword.getValue(), password.getValue())).isTrue();
    }
}