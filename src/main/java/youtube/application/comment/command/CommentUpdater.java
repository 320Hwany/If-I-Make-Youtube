package youtube.application.comment.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.comment.persist.Comment;
import youtube.repository.comment.CommentRepository;

@Service
public class CommentUpdater {

    private final CommentRepository commentRepository;

    public CommentUpdater(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void pressLike(final long commentId) {
        Comment comment = commentRepository.getByIdWithPessimisticLock(commentId);
        comment.pressLike();
    }
}
