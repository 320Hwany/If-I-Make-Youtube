package youtube.application.video.video_info.media_type.implement;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import youtube.application.video.video_info.media_type.MediaTypeForExtension;
import youtube.application.video.video_info.VideoInfoReader;
import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.global.exception.BadRequestException;

import java.util.List;

import static youtube.global.constant.ExceptionMessageConstant.MEDIA_TYPE_BAD_REQUEST;

@Service
public class MediaTypeReader {

    private final List<MediaTypeForExtension> mediaTypeForExtensions;
    private final VideoInfoReader videoInfoReader;

    public MediaTypeReader(final List<MediaTypeForExtension> mediaTypeForExtensions,
                           final VideoInfoReader videoInfoReader) {
        this.mediaTypeForExtensions = mediaTypeForExtensions;
        this.videoInfoReader = videoInfoReader;
    }

    public MediaType getByVideoInfoId(final long videoInfoId) {
        VideoInfo videoInfo = videoInfoReader.getByVideoInfoId(videoInfoId);
        String fileExtension = videoInfo.getFileExtension();

        for (MediaTypeForExtension mediaTypeForExtension : mediaTypeForExtensions) {
            if (mediaTypeForExtension.isSupport(fileExtension)) {
                return mediaTypeForExtension.getMediaType();
            }
        }
        throw new BadRequestException(MEDIA_TYPE_BAD_REQUEST.message);
    }
}
