package youtube.repository.video.video_watch_later;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.video.video_watch_later.persist.VideoWatchLater;

import java.util.Optional;

public interface VideoWatchLaterJpaRepository extends JpaRepository<VideoWatchLater, Long> {

    boolean existsByMemberIdAndVideoInfoId(final long memberId, final long videoInfoId);

    Optional<VideoWatchLater> findByMemberIdAndVideoInfoId(final long memberId, final long videoInfoId);
}
