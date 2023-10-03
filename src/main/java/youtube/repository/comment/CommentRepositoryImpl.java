package youtube.repository.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import org.springframework.stereotype.Repository;
import youtube.domain.comment.Comment;
import youtube.domain.comment.QComment;
import youtube.global.exception.NotFoundException;
import youtube.mapper.comment.dto.CommentResponse;
import youtube.mapper.comment.dto.QCommentResponse;

import java.util.List;

import static youtube.domain.comment.QComment.*;
import static youtube.global.constant.ExceptionMessageConstant.*;
import static youtube.global.constant.NumberConstant.TWENTY;

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
    public List<CommentResponse> findCommentResponsesOrderByLikes(final long videoInfoId, final long page) {
        return queryFactory.select(
                        new QCommentResponse(
                                comment.nickname.nickname,
                                comment.content,
                                comment.childCommentCount,
                                comment.likesCount,
                                comment.createdAt
                        ))
                .from(comment)
                .where(comment.videoInfoId.eq(videoInfoId))
                .orderBy(comment.likesCount.desc())
                .offset(page * TWENTY.value)
                .limit(TWENTY.value)
                .fetch();
    }

    @Override
    public List<CommentResponse> findCommentResponsesOrderByLatest(final long videoInfoId, final long page) {
        return queryFactory.select(
                        new QCommentResponse(
                                comment.nickname.nickname,
                                comment.content,
                                comment.childCommentCount,
                                comment.likesCount,
                                comment.createdAt
                        ))
                .from(comment)
                .where(comment.videoInfoId.eq(videoInfoId))
                .orderBy(comment.id.desc())
                .offset(page * TWENTY.value)
                .limit(TWENTY.value)
                .fetch();
    }

    @Override
    public List<CommentResponse> findChildCommentResponses(final long commentId) {
        return queryFactory.select(
                        new QCommentResponse(
                                comment.nickname.nickname,
                                comment.content,
                                comment.childCommentCount,
                                comment.likesCount,
                                comment.createdAt
                        ))
                .from(comment)
                .where(comment.parentId.eq(commentId))
                .orderBy(comment.likesCount.desc())
                .fetch();
    }

    @Override
    public long count() {
        return commentJpaRepository.count();
    }
}
