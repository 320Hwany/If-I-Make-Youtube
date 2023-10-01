package youtube.presentation.comment;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import youtube.application.comment.command.CommentCreator;
import youtube.application.comment.command.CommentUpdater;
import youtube.application.comment.query.CommentReader;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;
import youtube.mapper.comment.CommentMapper;
import youtube.mapper.comment.dto.CommentResponse;
import youtube.mapper.comment.dto.CommentResult;
import youtube.mapper.comment.dto.CommentSaveRequest;

import java.util.List;

@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentCreator commentCreator;
    private final CommentUpdater commentUpdater;
    private final CommentReader commentReader;

    public CommentController(final CommentCreator commentCreator,
                             final CommentUpdater commentUpdater,
                             final CommentReader commentReader) {
        this.commentCreator = commentCreator;
        this.commentUpdater = commentUpdater;
        this.commentReader = commentReader;
    }

    @GetMapping("/v1/comments/{videoInfoId}/{page}")
    public CommentResult findCommentResponses(@PathVariable final long videoInfoId,
                                              @PathVariable final long page) {
        List<CommentResponse> commentResponses =
                commentReader.findCommentResponsesOrderByLikes(videoInfoId, page);
        return CommentMapper.toCommentResult(page, commentResponses);
    }

    @PostMapping("/v2/comments")
    public void commentSave(@Login final MemberSession memberSession,
                            @RequestBody @Valid final CommentSaveRequest dto) {
        commentCreator.command(memberSession.id(), dto);
    }

    @PatchMapping("/v2/comments/likes/{commentId}")
    public void commentPressLike(@PathVariable @Valid final long commentId) {
        commentUpdater.pressLike(commentId);
    }
}
