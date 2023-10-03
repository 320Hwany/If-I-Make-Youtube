package youtube.application.comment.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.mapper.comment.dto.CommentResponse;
import youtube.repository.comment.CommentRepository;

import java.util.List;

@Service
public class ChildCommentReader {

    private final CommentRepository commentRepository;

    public ChildCommentReader(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findChildComments(final long commentId) {
        return commentRepository.findChildCommentResponses(commentId);
    }
}
