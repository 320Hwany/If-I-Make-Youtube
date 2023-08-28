package youtube.mapper.video_info;

import youtube.domain.video_info.persist.VideoInfo;
import youtube.mapper.video_info.dto.VideoInfoSaveRequest;

import static youtube.global.constant.NumberConstant.*;

public class VideoInfoMapper {

    private VideoInfoMapper() {
    }

    public static VideoInfo toEntity(final long channelId, final VideoInfoSaveRequest dto,
                                     final String fileExtension) {
        return VideoInfo.builder()
                .channelId(channelId)
                .videoTitle(dto.videoTitle())
                .videoType(dto.videoType())
                .views(ZERO.value)
                .likesCount(ZERO.value)
                .dislikesCount(ZERO.value)
                .contentCount(ZERO.value)
                .videoDescription(dto.videoDescription())
                .fileExtension(fileExtension)
                .build();
    }
}
