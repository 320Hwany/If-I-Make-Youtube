package youtube.application.video_info.command;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.video_info.persist.VideoInfo;
import youtube.domain.video_info.vo.VideoInfoCache;
import youtube.repository.video_info.VideoInfoRepository;

import java.util.concurrent.CompletableFuture;

@Service
public class CommandVideoInfoCacheUpdate {

    private final VideoInfoRepository videoInfoRepository;

    public CommandVideoInfoCacheUpdate(final VideoInfoRepository videoInfoRepository) {
        this.videoInfoRepository = videoInfoRepository;
    }

    // 캐시를 DB와 동기화하는 작업 비동기로 실행 - 스케줄러 안에서 사용되는 메소드
    @Async
    @Transactional
    public CompletableFuture<Void> command(final long videoInfoId, final VideoInfoCache cache) {
        VideoInfo entity = videoInfoRepository.getById(videoInfoId);
        entity.updateCacheInfo(cache);
        return CompletableFuture.completedFuture(null);
    }
}
