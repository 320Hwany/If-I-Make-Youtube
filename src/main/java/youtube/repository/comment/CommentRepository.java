package youtube.repository.comment;

import youtube.domain.comment.Comment;
import youtube.mapper.comment.dto.CommentResponse;

import java.util.List;

public interface CommentRepository {

    void save(final Comment comment);

    Comment getById(final long commentId);

    Comment getByIdWithPessimisticLock(final long commentId);

    List<CommentResponse> findCommentResponsesOrderByLikes(final long videoInfoId, final long page);

    long count();
}
