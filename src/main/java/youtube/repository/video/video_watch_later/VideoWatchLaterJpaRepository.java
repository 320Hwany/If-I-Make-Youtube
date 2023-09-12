package youtube.repository.video.video_watch_later;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.video.video_watch_later.VideoWatchLater;

public interface VideoWatchLaterJpaRepository extends JpaRepository<VideoWatchLater, Long> {
}
