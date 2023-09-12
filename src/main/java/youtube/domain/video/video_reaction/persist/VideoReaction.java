package youtube.domain.video.video_reaction.persist;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.domain.BaseTimeEntity;
import youtube.domain.video.video_reaction.vo.Reaction;
import youtube.global.annotation.Association;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class VideoReaction extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_reaction_id")
    private Long id;

    @Association
    private Long memberId;

    @Association
    private Long videoInfoId;

    @Enumerated(EnumType.STRING)
    private Reaction reaction;

    @Builder
    private VideoReaction(final Long memberId, final Long videoInfoId, final Reaction reaction) {
        this.memberId = memberId;
        this.videoInfoId = videoInfoId;
        this.reaction = reaction;
    }
}
