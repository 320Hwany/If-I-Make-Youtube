package youtube.application.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.jwt.JwtRefreshToken;
import youtube.repository.jwt.JwtRepository;
import youtube.util.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static youtube.global.constant.JwtConstant.REFRESH_TOKEN;
import static youtube.util.TestConstant.*;

@AcceptanceTest
class JwtFacadeTest {

    @Autowired
    private JwtFacade jwtFacade;

    @Autowired
    private JwtRepository jwtRepository;

    @Test
    @DisplayName("이미 회원 Id에 대한 RefreshToken이 DB에 있다면 RefreshToken을 업데이트합니다")
    void updateJwtRefreshToken() {
        // given
        JwtRefreshToken entity = JwtRefreshToken.builder()
                .memberId(1L)
                .refreshToken(REFRESH_TOKEN.value)
                .build();

        jwtRepository.save(entity);

        // when
        jwtFacade.saveJwtRefreshToken(1, UPDATE_REFRESH_TOKEN.value);
        JwtRefreshToken jwtRefreshToken = jwtRepository.getByMemberId(1L);

        // then
        assertThat(jwtRepository.count()).isEqualTo(1);
        assertThat(jwtRefreshToken.getRefreshToken()).isEqualTo(UPDATE_REFRESH_TOKEN.value);
    }

    @Test
    @DisplayName("회원 Id에 대한 RefreshToken이 DB에 없다면 RefreshToken을 새로 저장합니다")
    void saveJwtRefreshToken() {
        // when
        jwtFacade.saveJwtRefreshToken(1, UPDATE_REFRESH_TOKEN.value);
        JwtRefreshToken jwtRefreshToken = jwtRepository.getByMemberId(1L);

        // then
        assertThat(jwtRepository.count()).isEqualTo(1);
        assertThat(jwtRefreshToken.getRefreshToken()).isEqualTo(UPDATE_REFRESH_TOKEN.value);
    }
}