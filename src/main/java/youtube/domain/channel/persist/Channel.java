package youtube.domain.channel.persist;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import youtube.domain.BaseTimeEntity;
import youtube.domain.channel.vo.Button;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;
import youtube.global.annotation.Association;

@Getter
@Table(name = "channel")
@Entity
public class Channel extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private Long id;

    @Association
    private Long memberId;

    @Embedded
    private ChannelName channelName;

    @Embedded
    private ChannelDescription channelDescription;

    private int videosCount;

    private int subscribersCount;

    @Enumerated(EnumType.STRING)
    private Button button;

    private Boolean isInfluencer;

    protected Channel() {
    }

    @Builder
    private Channel(final long memberId, final ChannelName channelName,
                    final ChannelDescription channelDescription, final int videosCount,
                    final int subscribersCount, final Button button, final Boolean isInfluencer) {
        this.memberId = memberId;
        this.channelName = channelName;
        this.channelDescription = channelDescription;
        this.videosCount = videosCount;
        this.subscribersCount = subscribersCount;
        this.button = button;
        this.isInfluencer = isInfluencer;
    }

    public void updateSubscribersCount(final int subscribersCount) {
        this.subscribersCount = subscribersCount;
    }

    public void updateButton(final Button updateButton) {
        if (this.button != updateButton) {
            this.button = updateButton;
        }
    }
}
