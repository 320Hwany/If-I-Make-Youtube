package youtube.repository.comment;

import org.springframework.stereotype.Repository;
import youtube.domain.comment.Comment;
import youtube.global.constant.ExceptionMessageConstant;
import youtube.global.exception.NotFoundException;

import static youtube.global.constant.ExceptionMessageConstant.*;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    public CommentRepositoryImpl(final CommentJpaRepository commentJpaRepository) {
        this.commentJpaRepository = commentJpaRepository;
    }

    @Override
    public void save(final Comment comment) {
        commentJpaRepository.save(comment);
    }

    @Override
    public Comment getById(final long commentId) {
        return commentJpaRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND.message));
    }

    @Override
    public long count() {
        return commentJpaRepository.count();
    }
}
