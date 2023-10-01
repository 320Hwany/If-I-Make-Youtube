package youtube.repository.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import org.springframework.stereotype.Repository;
import youtube.domain.comment.persist.Comment;
import youtube.domain.comment.persist.QComment;
import youtube.global.exception.NotFoundException;

import static youtube.domain.comment.persist.QComment.*;
import static youtube.global.constant.ExceptionMessageConstant.*;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;
    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(final CommentJpaRepository commentJpaRepository,
                                 final JPAQueryFactory queryFactory) {
        this.commentJpaRepository = commentJpaRepository;
        this.queryFactory = queryFactory;
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
    public Comment getByIdWithPessimisticLock(final long commentId) {
        Comment comment = queryFactory.selectFrom(QComment.comment)
                .where(QComment.comment.id.eq(commentId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetchFirst();

        if (comment == null) {
            throw new NotFoundException(COMMENT_NOT_FOUND.message);
        }

        return comment;
    }

    @Override
    public long count() {
        return commentJpaRepository.count();
    }
}
