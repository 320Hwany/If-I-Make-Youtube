package youtube.mapper.membership;

import youtube.domain.membership.persist.Membership;
import youtube.domain.membership.vo.MembershipLevel;

import java.time.LocalDateTime;

public enum MembershipMapper {

    MembershipMapper() {
    };

    private static final int ZERO = 0;
    private static final MembershipLevel START_LEVEL = MembershipLevel.BRONZE;

    public static Membership toEntity(final long memberId, final long channelId) {
        LocalDateTime startJoinDateTime = LocalDateTime.now();

        return Membership.builder()
                .memberId(memberId)
                .channelId(channelId)
                .membershipLevel(START_LEVEL)
                .subscriptionPeriod(ZERO)
                .joinDateTime(startJoinDateTime)
                .build();
    }
}
