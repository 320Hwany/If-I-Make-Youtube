package youtube.domain.channel.persist;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.domain.BaseTimeEntity;
import youtube.domain.channel.vo.Button;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;
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

    @Embedded
    private ChannelName channelName;

    @Embedded
    private ChannelDescription channelDescription;

    private int videosCount;

    private int subscribersCount;

    @Enumerated(EnumType.STRING)
    private Button button;

    @Builder
    private Channel(final Long memberId, final ChannelName channelName,
                    final ChannelDescription channelDescription, final int videosCount,
                    final int subscribersCount, final Button button) {
        this.memberId = memberId;
        this.channelName = channelName;
        this.channelDescription = channelDescription;
        this.videosCount = videosCount;
        this.subscribersCount = subscribersCount;
        this.button = button;
    }
}
