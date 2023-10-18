package youtube.application.comment.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.mapper.comment.dto.CommentResponse;
import youtube.repository.comment.CommentRepository;

import java.util.List;

@Service
public class CommentReader {

    private final CommentRepository commentRepository;

    public CommentReader(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findCommentResponsesOrderByLikes(final long videoInfoId, final long page) {
        return commentRepository.findCommentResponsesOrderByLikes(videoInfoId, page);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findCommentResponsesOrderByLatest(final long videoInfoId, final long page) {
        return commentRepository.findCommentResponsesOrderByLatest(videoInfoId, page);
    }
}
