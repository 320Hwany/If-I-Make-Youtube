package youtube.mapper.video.video_info.dto;

import lombok.Builder;

@Builder
public record VideoInfoCacheUpdateDto(
        String videoTitle,
        String videoDescription,
        long views,
        long likesCount,
        long disLikesCount,
        long commentCount
) {
}
