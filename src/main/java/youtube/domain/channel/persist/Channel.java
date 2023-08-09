package youtube.domain.channel.persist;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.domain.BaseTimeEntity;
import youtube.domain.channel.vo.Button;
import youtube.global.annotation.IndirectReference;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Channel extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private Long id;

    @IndirectReference
    private Long memberId;

    private String channelName;

    private int videosCount;

    private int subscribersCount;

    @Lob
    private String channelDescription;

    @Enumerated(EnumType.STRING)
    private Button button;

    @Builder
    private Channel(final Long memberId, final String channelName, final int videosCount,
                   final int subscribersCount, final String channelDescription, final Button button) {
        this.memberId = memberId;
        this.channelName = channelName;
        this.videosCount = videosCount;
        this.subscribersCount = subscribersCount;
        this.channelDescription = channelDescription;
        this.button = button;
    }
}
