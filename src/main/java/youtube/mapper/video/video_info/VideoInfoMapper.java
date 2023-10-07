package youtube.mapper.video.video_info;

import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.domain.video.video_info.vo.VideoInfoCache;
import youtube.mapper.video.video_info.dto.VideoInfoCacheDto;
import youtube.mapper.video.video_info.dto.VideoInfoCacheUpdateDto;
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

    public static VideoInfoCacheUpdateDto toVideoInfoCacheUpdateDto(final VideoInfoCache cache) {
        AtomicLong atomicLikesCount = cache.getLikesCount();
        AtomicLong atomicDislikesCount = cache.getDislikesCount();
        AtomicLong atomicCommentCount = cache.getCommentCount();

        return VideoInfoCacheUpdateDto.builder()
                .videoTitle(cache.getVideoTitle())
                .videoDescription(cache.getVideoDescription())
                .views(cache.getViews())
                .likesCount(atomicLikesCount.get())
                .disLikesCount(atomicDislikesCount.get())
                .commentCount(atomicCommentCount.get())
                .build();
    }

    public static VideoInfoCache toCache(final VideoInfoCacheDto dto) {
        long atomicLikesCount = dto.likesCount();
        long atomicDisLikesCount = dto.dislikesCount();
        long atomicCommentCount = dto.commentCount();

        return VideoInfoCache.builder()
                .videoTitle(dto.videoTitle())
                .videoDescription(dto.videoDescription())
                .views(dto.views())
                .likesCount(toAtomicType(atomicLikesCount))
                .dislikesCount(toAtomicType(atomicDisLikesCount))
                .commentCount(toAtomicType(atomicCommentCount))
                .createdAt(dto.createdAt())
                .build();
    }

    private static AtomicLong toAtomicType(final long count) {
        return new AtomicLong(count);
    }
}
