package youtube.mapper.video.dto;

import lombok.Builder;
import youtube.domain.video_info.vo.VideoType;

@Builder
public record VideoSaveRequest(
        String videoTitle,
        VideoType videoType,
        String videoDescription
) {
}
