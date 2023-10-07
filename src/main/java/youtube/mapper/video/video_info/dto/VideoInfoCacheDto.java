package youtube.mapper.video.video_info.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record VideoInfoCacheDto(
        String videoTitle,

        String videoDescription,

        long views,

        long likesCount,

        long dislikesCount,
        long commentCount,

        LocalDateTime createdAt
) {

    @QueryProjection
    public VideoInfoCacheDto {
    }
}
