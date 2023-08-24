package youtube.mapper.video;

import youtube.domain.video.persist.Video;
import youtube.mapper.video.dto.VideoSaveRequest;

import static youtube.global.constant.NumberConstant.*;

public class VideoMapper {

    private VideoMapper() {
    }

    public static Video toEntity(final long channelId, final VideoSaveRequest dto) {
        return Video.builder()
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
