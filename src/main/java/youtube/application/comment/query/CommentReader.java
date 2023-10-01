package youtube.application.comment.query;

import org.springframework.stereotype.Service;
import youtube.repository.comment.CommentRepository;

@Service
public class CommentReader {

    private final CommentRepository commentRepository;

    public CommentReader(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
