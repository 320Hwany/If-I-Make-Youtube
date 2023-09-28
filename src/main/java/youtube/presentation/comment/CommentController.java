package youtube.presentation.comment;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import youtube.application.comment.command.CommentCreator;
import youtube.application.comment.command.CommentUpdater;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;
import youtube.mapper.comment.dto.CommentSaveRequest;

@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentCreator commentCreator;
    private final CommentUpdater commentUpdater;

    public CommentController(final CommentCreator commentCreator, final CommentUpdater commentUpdater) {
        this.commentCreator = commentCreator;
        this.commentUpdater = commentUpdater;
    }

    @PostMapping("/comments")
    public void commentSave(@Login final MemberSession memberSession,
                            @RequestBody @Valid final CommentSaveRequest dto) {
        commentCreator.command(memberSession.id(), dto);
    }

    @PatchMapping("/comments/likes/{commentId}")
    public void commentPressLike(@PathVariable @Valid final long commentId) {
        commentUpdater.pressLike(commentId);
    }
}
