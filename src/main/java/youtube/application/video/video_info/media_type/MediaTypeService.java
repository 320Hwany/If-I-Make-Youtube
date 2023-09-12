package youtube.application.video.video_info.media_type;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import youtube.application.video.video_info.query.QueryVideoInfoById;
import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.global.exception.BadRequestException;

import java.util.List;

import static youtube.global.constant.ExceptionMessageConstant.MEDIA_TYPE_BAD_REQUEST;

@Service
public class MediaTypeService {

    private final List<MediaTypeForExtension> mediaTypeForExtensions;
    private final QueryVideoInfoById queryVideoInfoById;

    public MediaTypeService(final List<MediaTypeForExtension> mediaTypeForExtensions,
                            final QueryVideoInfoById queryVideoInfoById) {
        this.mediaTypeForExtensions = mediaTypeForExtensions;
        this.queryVideoInfoById = queryVideoInfoById;
    }

    public MediaType getMediaType(final long videoInfoId) {
        VideoInfo videoInfo = queryVideoInfoById.query(videoInfoId);
        String fileExtension = videoInfo.getFileExtension();

        for (MediaTypeForExtension mediaTypeForExtension : mediaTypeForExtensions) {
            if (mediaTypeForExtension.isSupport(fileExtension)) {
                return mediaTypeForExtension.getMediaType();
            }
        }
        throw new BadRequestException(MEDIA_TYPE_BAD_REQUEST.message);
    }
}
