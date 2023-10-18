package youtube.domain.membership.persist;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import youtube.domain.membership.vo.MembershipLevel;
import youtube.global.annotation.Association;

import java.time.LocalDateTime;

@Getter
@Entity
public class Membership {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_id")
    private Long id;

    @Association
    private Long memberId;

    @Association
    private Long channelId;

    @Enumerated(EnumType.STRING)
    private MembershipLevel membershipLevel;

    private int subscriptionPeriod;

    private LocalDateTime joinDate;

    protected Membership() {
    }

    @Builder
    private Membership(final Long memberId, final Long channelId, final MembershipLevel membershipLevel,
                      final int subscriptionPeriod, final LocalDateTime joinDate) {
        this.memberId = memberId;
        this.channelId = channelId;
        this.membershipLevel = membershipLevel;
        this.subscriptionPeriod = subscriptionPeriod;
        this.joinDate = joinDate;
    }
}
