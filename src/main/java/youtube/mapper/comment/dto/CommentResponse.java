package youtube.mapper.comment.dto;

import com.querydsl.core.annotations.QueryProjection;
import youtube.domain.member.vo.Nickname;

import java.time.LocalDateTime;

public record CommentResponse(
        Nickname nickname,
        String content,
        long childCommentCount,
        long likesCount,
        LocalDateTime createdAt
) {
    @QueryProjection
    public CommentResponse {
    }
}
