package youtube.repository.video.video_watched;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.video.video_watched.persist.VideoWatched;

public interface VideoWatchedJpaRepository extends JpaRepository<VideoWatched, Long> {
}
