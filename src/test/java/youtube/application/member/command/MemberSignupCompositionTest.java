package youtube.application.member.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.application.member.composition.MemberSignupComposition;
import youtube.domain.member.vo.Gender;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.global.exception.BadRequestException;
import youtube.mapper.member.dto.MemberSignupRequest;
import youtube.util.AcceptanceTest;
import youtube.util.ServiceTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static youtube.util.TestConstant.*;

@AcceptanceTest
class MemberSignupCompositionTest extends ServiceTest {

    @Autowired
    private MemberSignupComposition memberSignupComposition;

    @Test
    @DisplayName("이미 가입된 회원이면 예외가 발생합니다")
    void signupFail() {
        // given 1
        saveMember();

        // given 2
        MemberSignupRequest dto = MemberSignupRequest.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .gender(Gender.MALE)
                .birthDate(LocalDate.now())
                .build();

        // expected
        assertThatThrownBy(() -> memberSignupComposition.signup(dto))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("회원 중복 검사를 체크한 후 이미 가입된 회원이 없으면 회원가입에 성공하며 채널이 하나 생성됩니다")
    void signupSuccess() {
        // given
        MemberSignupRequest dto = MemberSignupRequest.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .gender(Gender.MALE)
                .birthDate(LocalDate.now())
                .build();

        // when
        memberSignupComposition.signup(dto);

        // then
        assertThat(memberRepository.count()).isEqualTo(1);
        assertThat(channelRepository.count()).isEqualTo(1);
    }
}