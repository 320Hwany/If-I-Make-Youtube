package youtube.repository.comment;

import youtube.domain.comment.Comment;

public interface CommentRepository {

    void save(final Comment comment);

    Comment getById(final long commentId);

    long count();
}
