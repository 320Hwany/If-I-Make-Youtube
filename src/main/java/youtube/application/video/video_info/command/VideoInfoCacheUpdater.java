package youtube.application.video.video_info.command;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.domain.video.video_info.vo.VideoInfoCache;
import youtube.mapper.video.video_info.VideoInfoMapper;
import youtube.mapper.video.video_info.dto.VideoInfoCacheUpdateDto;
import youtube.repository.video.video_info.VideoInfoRepository;

import java.util.concurrent.CompletableFuture;

@Service
public class VideoInfoCacheUpdater {

    private final VideoInfoRepository videoInfoRepository;

    public VideoInfoCacheUpdater(final VideoInfoRepository videoInfoRepository) {
        this.videoInfoRepository = videoInfoRepository;
    }

    // 캐시를 DB와 동기화하는 작업 비동기로 실행 - 스케줄러 안에서 사용되는 메소드
    @Async
    @Transactional
    public CompletableFuture<Void> command(final long videoInfoId, final VideoInfoCache cache) {
        VideoInfoCacheUpdateDto dto = VideoInfoMapper.toVideoInfoCacheUpdateDto(cache);
        VideoInfo entity = videoInfoRepository.getById(videoInfoId);
        entity.updateCacheInfo(dto);
        return CompletableFuture.completedFuture(null);
    }
}
