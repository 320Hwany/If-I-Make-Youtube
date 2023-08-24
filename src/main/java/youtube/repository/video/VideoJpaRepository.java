package youtube.repository.video;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.video.persist.Video;

public interface VideoJpaRepository extends JpaRepository<Video, Long> {
}
