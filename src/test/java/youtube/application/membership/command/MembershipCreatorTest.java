package youtube.application.membership.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.application.membership.MembershipCreator;
import youtube.util.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;

class MembershipCreatorTest extends ServiceTest {

    @Autowired
    protected MembershipCreator membershipCreator;

    @Test
    @DisplayName("현재 로그인 한 회원이 특정 채널에 멤버십 가입을 할 수 있습니다")
    void joinMembership() {
        // given
        long memberId = 1L;
        long channelId = 1L;

        // when
        membershipCreator.joinMembership(memberId, channelId);

        // then
        assertThat(membershipRepository.count()).isEqualTo(1);
    }
}