package youtube.application.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.member.vo.MemberSession;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.RoleType;
import youtube.util.ServiceTest;
import youtube.util.TestConstant;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static youtube.global.constant.JwtKey.JWT_KEY;
import static youtube.global.constant.TimeConstant.ONE_HOUR;

class CreateTokenServiceTest extends ServiceTest {

    @Autowired
    private CreateTokenService createTokenService;

    @Test
    @DisplayName("MemberSession 정보를 가진 AccessToken을 생성합니다")
    void createAccessToken() throws JsonProcessingException {
        // given 1
        MemberSession memberSession = MemberSession.builder()
                .id(1)
                .nickname(Nickname.from(TestConstant.TEST_NICKNAME.value))
                .roleType(RoleType.PREMIUM)
                .likedVideosCount(10)
                .watchLaterVideosCount(20)
                .build();

        // given 2
        long expired = ONE_HOUR.value;
        byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY);

        // when
        String accessToken = createTokenService.createAccessToken(memberSession, expired);

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(decodedKey)
                .build()
                .parseClaimsJws(accessToken);

        String subject = claimsJws.getBody().getSubject();

        MemberSession memberSessionFromToken = objectMapper.readValue(subject, MemberSession.class);

        // then
        assertThat(memberSession).isEqualTo(memberSessionFromToken);
    }

    @Test
    @DisplayName("memberId를 가진 RefreshToken을 생성합니다")
    void createRefreshToken() {
        // given
        long memberId = 1;
        long expired = ONE_HOUR.value;
        byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY);

        // when
        String refreshToken = createTokenService.createRefreshToken(memberId, expired);

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(decodedKey)
                .build()
                .parseClaimsJws(refreshToken);

        String subject = claimsJws.getBody().getSubject();

        // then
        assertThat(Long.parseLong(subject)).isEqualTo(memberId);
    }
}