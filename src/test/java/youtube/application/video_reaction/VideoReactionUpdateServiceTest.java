package youtube.application.video_reaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.member.persist.Member;
import youtube.domain.video_info.persist.VideoInfo;
import youtube.domain.video_info.vo.VideoInfoCache;
import youtube.domain.video_reaction.vo.Reaction;
import youtube.mapper.video_reaction.dto.VideoReactionRequest;
import youtube.util.ServiceTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VideoReactionUpdateServiceTest extends ServiceTest {

    @Autowired
    private VideoReactionUpdateService videoReactionUpdateService;

    @Test
    @DisplayName("10명의 사용자가 같은 동영상에 동시에 좋아요를 누르면 좋아요 수가 10입니다 - 동시성 문제")
    void updateCache() throws ExecutionException, InterruptedException {
        // given 1
        Member member = saveMember();

        // given 2
        VideoInfo videoInfo = VideoInfo.builder()
                .channelId(1L)
                .videoTitle("동영상 제목")
                .build();

        videoInfoRepository.save(videoInfo);

        VideoReactionRequest dto = VideoReactionRequest.builder()
                .videoInfoId(videoInfo.getId())
                .originalReaction(Reaction.NO_REACTION)
                .updateReaction(Reaction.LIKE)
                .build();

        // given 3 - cache
        VideoInfoCache videoInfoCache = queryVideoInfoCacheById.query(videoInfo.getId());

        // given 4 - thread
        int numThreads = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // when
        for (int i = 0; i < numThreads; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                videoReactionUpdateService.saveReaction(member.getId(), dto);
            }, executorService);

            futures.add(future);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.get(); // Wait for all threads to complete

        executorService.shutdown();

        // then
        assertThat(videoInfoCache.getLikesCount()).isEqualTo(10);
        assertThat(videoReactionRepository.count()).isEqualTo(10);
    }
}