package youtube.mapper.video.video_info.dto;

import lombok.Builder;
import youtube.domain.video.video_info.vo.VideoType;

@Builder
public record VideoInfoSaveRequest(
        String videoTitle,
        VideoType videoType,
        String videoDescription
) {
}
