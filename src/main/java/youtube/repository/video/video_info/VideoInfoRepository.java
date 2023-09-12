package youtube.repository.video.video_info;

import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.mapper.video.video_info.dto.VideoInfoCacheDto;

public interface VideoInfoRepository {

    void save(final VideoInfo videoInfo);

    VideoInfo getById(final long videoInfoId);

    VideoInfoCacheDto getVideoInfoCacheDtoById(final long videoInfoId);

    long count();
}
