package youtube.repository.video_info;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.video_info.persist.VideoInfo;

public interface VideoInfoJpaRepository extends JpaRepository<VideoInfo, Long> {
}
