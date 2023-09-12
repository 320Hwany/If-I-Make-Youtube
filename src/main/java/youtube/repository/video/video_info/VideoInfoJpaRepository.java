package youtube.repository.video.video_info;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.video.video_info.persist.VideoInfo;

public interface VideoInfoJpaRepository extends JpaRepository<VideoInfo, Long> {
}
