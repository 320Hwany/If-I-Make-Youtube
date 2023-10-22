package youtube.application.comment.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.application.comment.CommentUpdater;
import youtube.domain.comment.Comment;
import youtube.domain.member.vo.Nickname;
import youtube.global.exception.NotFoundException;
import youtube.util.ServiceTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;
import static youtube.util.TestConstant.*;

class CommentUpdaterTest extends ServiceTest {

    @Autowired
    private CommentUpdater commentUpdater;

    @Test
    @DisplayName("존재하지 않는 댓글 아이디에 좋아요를 누르면 예외가 발생합니다")
    void pressLikeNotFound() {
        // expected
        assertThatThrownBy(() -> commentUpdater.pressLike(9999))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("댓글에 좋아요를 누르면 좋아요 수가 1 증가합니다")
    void pressLike() {
        // given
        Comment comment = Comment.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .likesCount(0)
                .build();

        commentRepository.save(comment);

        // when
        commentUpdater.pressLike(comment.getId());

        // then
        Comment psComment = commentRepository.getById(comment.getId());
        assertThat(psComment.getLikesCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("여러 명의 사용자가 동시에 같은 댓글에 좋아요를 눌러도 좋아요 수가 누락되지 않고 동시성 문제를 처리합니다")
    void pressLikeConcurrencyTest() throws Exception {
        // given
        Comment comment = Comment.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .likesCount(0)
                .build();

        commentRepository.save(comment);

        // given2 - thread
        int numThreads = 10;
        int incrementsPerThread = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // when
        for (int i = 0; i < numThreads; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    commentUpdater.pressLike(comment.getId());
                }
            }, executorService);

            futures.add(future);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.get();

        executorService.shutdown();

        // then
        Comment psComment = commentRepository.getById(comment.getId());
        int expectedCount = incrementsPerThread * numThreads;
        assertThat(psComment.getLikesCount()).isEqualTo(expectedCount);
    }
}