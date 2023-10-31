package youtube.application.membership;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.membership.persist.Membership;
import youtube.domain.membership.vo.MembershipLevel;
import youtube.global.exception.NotFoundException;
import youtube.util.ServiceTest;

import static org.assertj.core.api.Assertions.*;

class MembershipDeleterTest extends ServiceTest {

    @Autowired
    protected MembershipDeleter membershipDeleter;

    @Test
    @DisplayName("회원이 가입된 채널의 멤버쉽을 탈퇴합니다")
    void withdrawSuccess() {
        // given
        Membership membership = Membership.builder()
                .memberId(1L)
                .channelId(1L)
                .membershipLevel(MembershipLevel.GOLD)
                .build();

        membershipRepository.save(membership);

        // when
        membershipDeleter.withdraw(1L, 1L);

        // then
        assertThat(membershipRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("회원이 가입된 채널의 멤버쉽이 존재하지 않으면 탈퇴에 실패합니다")
    void withdrawFail() {
        // when
        assertThatThrownBy(() -> membershipDeleter.withdraw(1L, 1L))
                .isInstanceOf(NotFoundException.class);
    }
}