package youtube.application.comment.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.comment.Comment;
import youtube.mapper.comment.CommentMapper;
import youtube.mapper.comment.dto.CommentSaveRequest;
import youtube.repository.comment.CommentRepository;

@Service
public class CommentCreator {

    private final CommentRepository commentRepository;

    public CommentCreator(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void command(final long memberId, final CommentSaveRequest dto) {
        Comment entity = CommentMapper.toEntity(memberId, dto);
        commentRepository.save(entity);
    }
}
