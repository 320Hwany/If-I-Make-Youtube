package youtube.domain.subscription;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import youtube.domain.BaseTimeEntity;
import youtube.global.annotation.Association;

@Getter
@Table(name = "subscription")
@Entity
public class Subscription extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long id;

    @Association
    private Long memberId;

    @Association
    private Long channelId;

    private Boolean isNotification;

    protected Subscription() {
    }

    @Builder
    private Subscription(final long memberId, final long channelId, final boolean isNotification) {
        this.memberId = memberId;
        this.channelId = channelId;
        this.isNotification = isNotification;
    }
}
