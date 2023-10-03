package youtube.domain.comment;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import youtube.domain.BaseTimeEntity;
import youtube.domain.member.vo.Nickname;
import youtube.global.annotation.Association;

import static youtube.global.constant.NumberConstant.*;

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

    @Embedded
    private Nickname nickname;

    private String content;

    private long childCommentCount;

    private long likesCount;

    protected Comment() {
    }

    @Builder
    private Comment(final long memberId, final long videoInfoId, final long parentId, final Nickname nickname,
                    final String content, final long childCommentCount, final long likesCount) {
        this.memberId = memberId;
        this.videoInfoId = videoInfoId;
        this.parentId = parentId;
        this.nickname = nickname;
        this.content = content;
        this.childCommentCount = childCommentCount;
        this.likesCount = likesCount;
    }

    public void pressLike() {
        this.likesCount += ONE.value;
    }
}
