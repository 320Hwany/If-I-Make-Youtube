package youtube.mapper.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import static youtube.global.constant.AnnotationMessageConstant.*;

@Builder
public record CommentSaveRequest(
        @NotNull(message = VIDEO_INFO_NULL)
        long videoInfoId,
        @NotBlank(message = CONTENT_BLANK)
        String content
) {
}
