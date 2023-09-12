package youtube.repository.video_watch_later;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.video_watch_later.VideoWatchLater;

public interface VideoWatchLaterJpaRepository extends JpaRepository<VideoWatchLater, Long> {
}
