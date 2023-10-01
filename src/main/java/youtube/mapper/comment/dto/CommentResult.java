package youtube.mapper.comment.dto;

import java.util.List;

public record CommentResult(
        long page,
        List<CommentResponse> commentResponses
) {
}
