package youtube.domain.membership.vo;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.MONTHS;

public enum MembershipLevel {

    BRONZE(0),
    SILVER(3),
    GOLD(6),
    PLATINUM(12),
    DIAMOND(24),
    RED_DIAMOND(Integer.MAX_VALUE);

    private final int minMonths;

    MembershipLevel(final int minMonths) {
        this.minMonths = minMonths;
    }

    public MembershipLevel calculateLevel(final LocalDateTime joinDateTime, final LocalDateTime updateDateTime) {
        long monthsBetween = MONTHS.between(joinDateTime, updateDateTime);

        for (MembershipLevel level : values()) {
            if (monthsBetween < level.minMonths) {
                return level;
            }
        }

        return RED_DIAMOND;
    }
}

