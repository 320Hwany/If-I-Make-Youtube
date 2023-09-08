package youtube.application.member.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.Password;
import youtube.global.exception.NotFoundException;
import youtube.util.ServiceTest;

import static org.assertj.core.api.Assertions.*;
import static youtube.util.TestConstant.*;

class CommandPasswordUpdateTest extends ServiceTest {

    @Autowired
    private CommandPasswordUpdate commandPasswordUpdate;

    @Test
    @DisplayName("로그인한 회원의 정보가 DB에 존재하지 않으면 비밀번호 변경에 실패합니다")
    void passwordUpdateFail() {
        // given
        long memberId = 9999L;
        Password password = Password.from(TEST_PASSWORD.value);

        // expected
        assertThatThrownBy(() -> commandPasswordUpdate.command(memberId, password))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("로그인한 회원의 비밀번호를 수정합니다")
    void passwordUpdateSuccess() {
        // given 1
        long memberId = saveMember();

        // given 2
        Password updatePassword = Password.from("수정 비밀번호!");

        // when
        commandPasswordUpdate.command(memberId, updatePassword);

        // then
        Member psEntity = memberRepository.getById(memberId);
        Password password = psEntity.getPassword();
        assertThat(passwordEncoder.matches(updatePassword.getPassword(), password.getPassword())).isTrue();
    }
}