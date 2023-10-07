package youtube.mapper.comment;

import youtube.domain.comment.Comment;
import youtube.domain.member.vo.MemberSession;
import youtube.domain.member.vo.Nickname;
import youtube.mapper.comment.dto.ChildCommentResult;
import youtube.mapper.comment.dto.CommentResponse;
import youtube.mapper.comment.dto.CommentResult;
import youtube.mapper.comment.dto.CommentSaveRequest;

import java.util.List;

public enum CommentMapper {

    CommentMapper() {
    };

    private static final long ZERO = 0;

    // parentId가 0이면 일반 댓글, 0이상이면 대댓글
    public static Comment toEntity(final MemberSession memberSession, final CommentSaveRequest dto) {
        return Comment.builder()
                .memberId(memberSession.id())
                .videoInfoId(dto.videoInfoId())
                .parentId(dto.parentId())
                .nickname(memberSession.nickname())
                .content(dto.content())
                .childCommentCount(ZERO)
                .likesCount(ZERO)
                .build();
    }

    public static CommentResult toCommentResult(final long page, final List<CommentResponse> commentResponses) {
        return new CommentResult(page, commentResponses);
    }

    public static ChildCommentResult toChildCommentResult(final List<CommentResponse> childCommentResponses) {
        return new ChildCommentResult(childCommentResponses);
    }
}
