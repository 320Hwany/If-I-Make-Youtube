package youtube.application.video.video_info.media_type.implement;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import youtube.application.video.video_info.media_type.MediaTypeForExtension;

import static youtube.global.constant.MediaTypeConstant.*;

@Service
public class MP4MediaType implements MediaTypeForExtension {

    @Override
    public boolean isSupport(final String fileExtension) {
        return fileExtension.equals(MP4_EXTENSION.value);
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.valueOf(VIDEO_MP4.value);
    }
}
