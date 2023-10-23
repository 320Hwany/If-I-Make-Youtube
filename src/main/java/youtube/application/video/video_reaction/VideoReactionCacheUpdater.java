package youtube.application.video.video_reaction;

import org.springframework.stereotype.Service;
import youtube.application.video.video_info.VideoInfoCacheReader;
import youtube.domain.video.video_info.vo.VideoInfoCache;
import youtube.mapper.video.video_reaction.dto.VideoReactionRequest;


@Service
public class VideoReactionCacheUpdater {

    private final VideoInfoCacheReader videoInfoCacheReader;

    public VideoReactionCacheUpdater(final VideoInfoCacheReader videoInfoCacheReader) {
        this.videoInfoCacheReader = videoInfoCacheReader;
    }

    public void updateCache(final VideoReactionRequest dto) {
        VideoInfoCache videoInfoCache = videoInfoCacheReader.getByVideoInfoId(dto.videoInfoId());
        videoInfoCache.updateLikesCount(dto.originalReaction(), dto.updateReaction());
        videoInfoCache.updateDisLikesCount(dto.originalReaction(), dto.updateReaction());
    }
}
