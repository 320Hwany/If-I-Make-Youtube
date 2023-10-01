package youtube.repository.comment;

import youtube.domain.comment.persist.Comment;

public interface CommentRepository {

    void save(final Comment comment);

    Comment getById(final long commentId);

    Comment getByIdWithPessimisticLock(final long commentId);

    long count();
}
