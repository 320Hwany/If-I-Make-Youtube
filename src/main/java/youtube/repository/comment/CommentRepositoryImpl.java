package youtube.repository.comment;

import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    public CommentRepositoryImpl(final CommentJpaRepository commentJpaRepository) {
        this.commentJpaRepository = commentJpaRepository;
    }
}
