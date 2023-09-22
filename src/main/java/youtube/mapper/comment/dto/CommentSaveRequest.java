package youtube.mapper.comment.dto;

import lombok.Builder;

@Builder
public record CommentSaveRequest(
        long videoInfoId,
        String content
) {
}
