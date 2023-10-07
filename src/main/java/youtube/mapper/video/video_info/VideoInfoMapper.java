package youtube.mapper.video.video_info;

import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.domain.video.video_info.vo.VideoInfoCache;
import youtube.mapper.video.video_info.dto.VideoInfoCacheDto;
import youtube.mapper.video.video_info.dto.VideoInfoSaveRequest;

import java.util.concurrent.atomic.AtomicLong;

import static youtube.global.constant.NumberConstant.*;

public enum VideoInfoMapper {

    VideoInfoMapper() {
    };

    public static VideoInfo toEntity(final long channelId, final VideoInfoSaveRequest dto,
                                     final String fileExtension) {
        return VideoInfo.builder()
                .channelId(channelId)
                .videoTitle(dto.videoTitle())
                .videoType(dto.videoType())
                .views(ZERO.value)
                .likesCount(ZERO.value)
                .dislikesCount(ZERO.value)
                .commentCount(ZERO.value)
                .videoDescription(dto.videoDescription())
                .fileExtension(fileExtension)
                .build();
    }

    public static VideoInfoCache toCache(final VideoInfoCacheDto dto) {
        long commentCount = dto.commentCount();

        return VideoInfoCache.builder()
                .videoTitle(dto.videoTitle())
                .videoDescription(dto.videoDescription())
                .views(dto.views())
                .likesCount(dto.likesCount())
                .dislikesCount(dto.dislikesCount())
                .commentCount(toAtomicType(commentCount))
                .createdAt(dto.createdAt())
                .build();
    }

    private static AtomicLong toAtomicType(final long commentCount) {
        return new AtomicLong(commentCount);
    }
}
