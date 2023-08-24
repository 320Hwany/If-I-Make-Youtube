package youtube.application.member.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.jwt.JwtRefreshToken;
import youtube.repository.jwt.JwtRepository;
import youtube.util.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static youtube.global.constant.StringConstant.REFRESH_TOKEN;

@AcceptanceTest
class CommandMemberLogoutTest {

    @Autowired
    private CommandMemberLogout commandMemberLogout;

    @Autowired
    private JwtRepository jwtRepository;

    @Test
    @DisplayName("회원 로그아웃을 하면 DB에 저장된 RefreshToken이 삭제됩니다")
    void memberLogout() {
        // given
        JwtRefreshToken entity = JwtRefreshToken.builder()
                .refreshToken(REFRESH_TOKEN.value)
                .memberId(1L)
                .build();

        jwtRepository.save(entity);

        // when
        commandMemberLogout.command(1L);

        // then
        assertThat(jwtRepository.count()).isEqualTo(0);
    }
}