package youtube.domain.video.video_reaction.persist;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import youtube.domain.BaseTimeEntity;
import youtube.domain.video.video_reaction.vo.Reaction;
import youtube.global.annotation.Association;

@Getter
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

    protected VideoReaction() {
    }

    @Builder
    private VideoReaction(final long memberId, final long videoInfoId, final Reaction reaction) {
        this.memberId = memberId;
        this.videoInfoId = videoInfoId;
        this.reaction = reaction;
    }

    public void updateReaction(final Reaction updateReaction) {
        this.reaction = updateReaction;
    }
}
