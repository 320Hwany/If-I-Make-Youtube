package youtube.mapper.video_info.dto;

import lombok.Builder;
import youtube.domain.video_info.vo.VideoType;

@Builder
public record VideoInfoSaveRequest(
        String videoTitle,
        VideoType videoType,
        String videoDescription
) {
}
