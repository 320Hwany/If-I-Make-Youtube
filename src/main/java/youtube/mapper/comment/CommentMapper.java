package youtube.mapper.comment;

import youtube.domain.comment.Comment;
import youtube.mapper.comment.dto.CommentSaveRequest;

public enum CommentMapper {

    CommentMapper() {
    };

    private static final long ZERO = 0;

    public static Comment toEntity(final long memberId, final CommentSaveRequest dto) {
        return Comment.builder()
                .memberId(memberId)
                .videoInfoId(dto.videoInfoId())
                .parentId(ZERO)
                .content(dto.content())
                .childContentCount(ZERO)
                .likesCount(ZERO)
                .build();
    }
}
