package youtube.repository.comment;

import org.springframework.stereotype.Repository;
import youtube.domain.comment.Comment;

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
}
