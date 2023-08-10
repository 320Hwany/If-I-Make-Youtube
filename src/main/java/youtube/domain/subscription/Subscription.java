package youtube.domain.subscription;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.domain.BaseTimeEntity;
import youtube.global.annotation.IndirectReference;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Subscription extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long id;

    @IndirectReference
    private Long memberId;

    @IndirectReference
    private Long channelId;

    private Boolean isNotification;

    @Builder
    private Subscription(final Long memberId, final Long channelId, final Boolean isNotification) {
        this.memberId = memberId;
        this.channelId = channelId;
        this.isNotification = isNotification;
    }
}
