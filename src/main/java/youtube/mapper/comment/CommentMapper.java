package youtube.mapper.comment;

import youtube.domain.comment.Comment;
import youtube.domain.member.vo.Nickname;
import youtube.mapper.comment.dto.CommentResponse;
import youtube.mapper.comment.dto.CommentResult;
import youtube.mapper.comment.dto.CommentSaveRequest;

import java.util.List;

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
                .childCommentCount(ZERO)
                .likesCount(ZERO)
                .build();
    }

    public static CommentResult toCommentResult(final long page, final List<CommentResponse> commentResponses) {
        return new CommentResult(page, commentResponses);
    }
}
