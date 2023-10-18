package youtube.mapper.membership;

import youtube.domain.membership.persist.Membership;
import youtube.domain.membership.vo.MembershipLevel;

import java.time.LocalDateTime;

public enum MembershipMapper {

    MembershipMapper() {
    };

    private static final int ZERO = 0;
    private static final MembershipLevel startLevel = MembershipLevel.BRONZE;
    private static final LocalDateTime startJoinDate = LocalDateTime.now();

    public static Membership toEntity(final long memberId, final long channelId) {

        return Membership.builder()
                .memberId(memberId)
                .channelId(channelId)
                .membershipLevel(startLevel)
                .subscriptionPeriod(ZERO)
                .joinDate(startJoinDate)
                .build();
    }
}
