package youtube.application.video_info.media_type;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import youtube.domain.video_info.persist.VideoInfo;
import youtube.global.exception.BadRequestException;
import youtube.repository.video_info.VideoInfoRepository;

import java.util.List;

import static youtube.global.constant.ExceptionMessageConstant.MEDIA_TYPE_BAD_REQUEST;

@Service
public class MediaTypeService {

    private final List<MediaTypeForExtension> mediaTypeForExtensions;
    private final VideoInfoRepository videoInfoRepository;

    public MediaTypeService(final List<MediaTypeForExtension> mediaTypeForExtensions,
                            final VideoInfoRepository videoInfoRepository) {
        this.mediaTypeForExtensions = mediaTypeForExtensions;
        this.videoInfoRepository = videoInfoRepository;
    }

    public MediaType getMediaType(final long videoInfoId) {
        VideoInfo videoInfo = videoInfoRepository.getById(videoInfoId);
        String fileExtension = videoInfo.getFileExtension();

        for (MediaTypeForExtension mediaTypeForExtension : mediaTypeForExtensions) {
            if (mediaTypeForExtension.isSupport(fileExtension)) {
                return mediaTypeForExtension.getMediaType();
            }
        }
        throw new BadRequestException(MEDIA_TYPE_BAD_REQUEST.message);
    }
}
