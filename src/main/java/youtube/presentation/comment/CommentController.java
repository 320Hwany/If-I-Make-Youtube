package youtube.presentation.comment;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import youtube.application.comment.command.CommentCreator;
import youtube.application.comment.command.CommentUpdater;
import youtube.application.comment.query.ChildCommentReader;
import youtube.application.comment.query.CommentReader;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;
import youtube.mapper.comment.CommentMapper;
import youtube.mapper.comment.dto.ChildCommentResult;
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
    private final ChildCommentReader childCommentReader;

    public CommentController(final CommentCreator commentCreator,
                             final CommentUpdater commentUpdater,
                             final CommentReader commentReader,
                             final ChildCommentReader childCommentReader) {
        this.commentCreator = commentCreator;
        this.commentUpdater = commentUpdater;
        this.commentReader = commentReader;
        this.childCommentReader = childCommentReader;
    }

    @GetMapping("/v1/comments-likes/{videoInfoId}/{page}")
    public CommentResult findCommentResponsesOrderByLikes(@PathVariable final long videoInfoId,
                                                          @PathVariable final long page) {
        List<CommentResponse> commentResponses =
                commentReader.findCommentResponsesOrderByLikes(videoInfoId, page);
        return CommentMapper.toCommentResult(page, commentResponses);
    }

    @GetMapping("/v1/comments-latest/{videoInfoId}/{page}")
    public CommentResult findCommentResponsesOrderByLatest(@PathVariable final long videoInfoId,
                                                           @PathVariable final long page) {
        List<CommentResponse> commentResponses =
                commentReader.findCommentResponsesOrderByLatest(videoInfoId, page);
        return CommentMapper.toCommentResult(page, commentResponses);
    }

    @GetMapping("/v1/child-comments-likes/{commentId}")
    public ChildCommentResult findChildCommentResponses(@PathVariable final long commentId) {
        List<CommentResponse> childComments = childCommentReader.findChildComments(commentId);
        return CommentMapper.toChildCommentResult(childComments);
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
