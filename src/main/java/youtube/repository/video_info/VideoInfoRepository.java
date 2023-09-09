package youtube.repository.video_info;

import youtube.domain.video_info.persist.VideoInfo;
import youtube.domain.video_info.vo.VideoInfoCache;

public interface VideoInfoRepository {

    void save(final VideoInfo videoInfo);

    VideoInfo getById(final long videoInfoId);

    VideoInfoCache getVideoInfoCacheDtoById(final long videoInfoId);

    long count();
}
