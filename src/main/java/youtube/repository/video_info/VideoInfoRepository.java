package youtube.repository.video_info;

import youtube.domain.video_info.persist.VideoInfo;

public interface VideoInfoRepository {

    void save(final VideoInfo videoInfo);

    VideoInfo getById(final long videoId);

    long count();
}
