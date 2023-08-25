package youtube.mapper.video;

import youtube.domain.video_info.persist.VideoInfo;
import youtube.mapper.video.dto.VideoSaveRequest;

import static youtube.global.constant.NumberConstant.*;

public class VideoMapper {

    private VideoMapper() {
    }

    public static VideoInfo toEntity(final long channelId, final VideoSaveRequest dto) {
        return VideoInfo.builder()
                .channelId(channelId)
                .videoTitle(dto.videoTitle())
                .videoType(dto.videoType())
                .views(ZERO.value)
                .likesCount(ZERO.value)
                .contentCount(ZERO.value)
                .videoDescription(dto.videoDescription())
                .build();
    }
}
