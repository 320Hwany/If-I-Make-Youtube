package youtube.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.comment.persist.Comment;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
}
