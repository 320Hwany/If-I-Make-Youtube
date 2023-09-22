package youtube.domain.comment;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import youtube.domain.BaseTimeEntity;
import youtube.global.annotation.Association;

@Getter
@Entity
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Association
    private Long memberId;

    @Association
    private Long videoInfoId;

    @Association
    private Long parentId;

    private String content;

    private long childContentCount;

    private long likesCount;

    protected Comment() {
    }

    @Builder
    private Comment(final Long memberId, final Long videoInfoId, final Long parentId,
                   final String content, final long childContentCount, final long likesCount) {
        this.memberId = memberId;
        this.videoInfoId = videoInfoId;
        this.parentId = parentId;
        this.content = content;
        this.childContentCount = childContentCount;
        this.likesCount = likesCount;
    }
}
