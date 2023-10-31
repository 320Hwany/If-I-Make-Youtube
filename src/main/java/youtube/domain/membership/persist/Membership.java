package youtube.domain.membership.persist;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import youtube.domain.BaseTimeEntity;
import youtube.domain.membership.vo.MembershipLevel;
import youtube.global.annotation.Association;

import java.time.LocalDateTime;

@Getter
@Entity
public class Membership extends BaseTimeEntity {

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

    private LocalDateTime joinDateTime;

    protected Membership() {
    }

    @Builder
    private Membership(final Long memberId, final Long channelId, final MembershipLevel membershipLevel,
                      final int subscriptionPeriod, final LocalDateTime joinDateTime) {
        this.memberId = memberId;
        this.channelId = channelId;
        this.membershipLevel = membershipLevel;
        this.subscriptionPeriod = subscriptionPeriod;
        this.joinDateTime = joinDateTime;
    }

    public void updateLevel(final LocalDateTime updateDateTime) {
        this.membershipLevel = membershipLevel.calculateLevel(joinDateTime, updateDateTime);
    }
}
