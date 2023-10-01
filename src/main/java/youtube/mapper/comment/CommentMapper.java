package youtube.mapper.comment;

import youtube.domain.comment.persist.Comment;
import youtube.domain.member.vo.Nickname;
import youtube.mapper.comment.dto.CommentSaveRequest;

public enum CommentMapper {

    CommentMapper() {
    };

    private static final long ZERO = 0;

    // parentId가 0이면 일반 댓글, 0이상이면 대댓글
    public static Comment toEntity(final long memberId, final Nickname nickname, final CommentSaveRequest dto) {
        return Comment.builder()
                .memberId(memberId)
                .videoInfoId(dto.videoInfoId())
                .parentId(dto.parentId())
                .nickname(nickname)
                .content(dto.content())
                .childContentCount(ZERO)
                .likesCount(ZERO)
                .build();
    }
}
