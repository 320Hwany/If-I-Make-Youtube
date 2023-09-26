package youtube.presentation.comment;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youtube.application.comment.command.CommentCreator;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;
import youtube.mapper.comment.dto.CommentSaveRequest;

@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentCreator commentCreator;

    public CommentController(final CommentCreator commentCreator) {
        this.commentCreator = commentCreator;
    }

    @PostMapping("/comments")
    public void commentSave(@Login final MemberSession memberSession,
                            @RequestBody @Valid final CommentSaveRequest dto) {
        commentCreator.command(memberSession.id(), dto);
    }
}
