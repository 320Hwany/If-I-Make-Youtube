package youtube.domain.video_reaction.persist;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.domain.BaseTimeEntity;
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

    @Builder
    private VideoReaction(final Long memberId, final Long videoInfoId) {
        this.memberId = memberId;
        this.videoInfoId = videoInfoId;
    }
}
