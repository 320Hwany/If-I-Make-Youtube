package youtube.repository.video.video_watch_later;

import org.springframework.stereotype.Repository;
import youtube.domain.video.video_watch_later.VideoWatchLater;
import youtube.global.exception.NotFoundException;

import static youtube.global.constant.ExceptionMessageConstant.*;


@Repository
public class VideoWatchLaterRepositoryImpl implements VideoWatchLaterRepository {

    private final VideoWatchLaterJpaRepository videoWatchLaterJpaRepository;

    public VideoWatchLaterRepositoryImpl(final VideoWatchLaterJpaRepository videoWatchLaterJpaRepository) {
        this.videoWatchLaterJpaRepository = videoWatchLaterJpaRepository;
    }

    @Override
    public void save(final VideoWatchLater videoWatchLater) {
        videoWatchLaterJpaRepository.save(videoWatchLater);
    }

    @Override
    public boolean existsByMemberIdAndVideoInfoId(final long memberId, final long videoInfoId) {
        return videoWatchLaterJpaRepository.existsByMemberIdAndVideoInfoId(memberId, videoInfoId);
    }

    @Override
    public VideoWatchLater getByMemberIdAndVideoInfoId(final long memberId, final long videoInfoId) {
        return videoWatchLaterJpaRepository.findByMemberIdAndVideoInfoId(memberId, videoInfoId)
                .orElseThrow(() -> new NotFoundException(VIDEO_WATCH_LATER_NOT_FOUND.message));
    }

    @Override
    public void delete(final VideoWatchLater videoWatchLater) {
        videoWatchLaterJpaRepository.delete(videoWatchLater);
    }

    @Override
    public long count() {
        return videoWatchLaterJpaRepository.count();
    }
}
