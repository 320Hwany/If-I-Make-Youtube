package youtube.mapper.comment.dto;

import java.util.List;

public record ChildCommentResult(
        List<CommentResponse> childCommentResponses
) {
}
